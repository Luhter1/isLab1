package org.itmo.isLab1.adminrequests;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.itmo.isLab1.adminrequests.dto.AdminRequestDto;
import org.itmo.isLab1.adminrequests.mapper.AdminRequestMapper;
import org.itmo.isLab1.adminrequests.policy.AdminRequestPolicy;
import org.itmo.isLab1.common.errors.AdminRequestAlreadyProcessed;
import org.itmo.isLab1.common.errors.ResourceNotFoundException;
import org.itmo.isLab1.common.errors.SomePendingRequestsExists;
import org.itmo.isLab1.events.EventService;
import org.itmo.isLab1.events.EventType;
import org.itmo.isLab1.users.Role;
import org.itmo.isLab1.users.User;
import org.itmo.isLab1.users.UserRepository;
import org.itmo.isLab1.users.UserService;

import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class AdminRequestService {

    private final AdminRequestRepository repository;
    private final AdminRequestMapper mapper;
    private final AdminRequestPolicy policy;
    private final UserRepository userRepository;
    private final UserService userService;
    private final EventService<AdminRequest> eventService;

    public Page<AdminRequestDto> getAll(Pageable pageable) {
        var user = currentUser();
        policy.showAll(user);

        if (user.isAdmin()) {
            return repository.findAllByOrderByCreatedAtDesc(pageable).map(mapper::map);
        }

        return repository.findAllByUserOrderByCreatedAtDesc(user, pageable).map(mapper::map);
    }

    public Page<AdminRequestDto> getAllPending(Pageable pageable) {
        policy.showAll(currentUser());

        var adminRequests = repository.findAllByStatusOrderByCreatedAtDesc(Status.PENDING, pageable);
        return adminRequests.map(mapper::map);
    }

    public AdminRequestDto getById(int id) {
        var adminRequest = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not Found: " + id));
        policy.show(currentUser(), adminRequest);

        return mapper.map(adminRequest);
    }

    @Transactional
    public AdminRequestDto create() {
        var user = currentUser();
        policy.create(user);

        var previousRequests = repository.findByStatusAndUserOrderByCreatedAtDesc(Status.PENDING, user);
        if (previousRequests.isPresent()) {
            throw new SomePendingRequestsExists("У вас уже есть необработанная заявка.");
        }

        var adminRequest = AdminRequest.builder()
            .user(user)
            .status(Status.PENDING)
            .createdAt(ZonedDateTime.now())
            .build();

        repository.save(adminRequest);
        eventService.notify(EventType.CREATE, adminRequest);
        return mapper.map(adminRequest);
    }

    @Transactional
    public AdminRequestDto process(int id, boolean approved) {
        var currentUser = currentUser();
        var adminRequest = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not Found: " + id));
        policy.update(currentUser, adminRequest);

        if (!adminRequest.getStatus().equals(Status.PENDING)) {
            throw new AdminRequestAlreadyProcessed("Запрос на администрирование уже был обработан.");
        }

        adminRequest.setApprovalDate(ZonedDateTime.now());
        adminRequest.setApprovedBy(currentUser);

        if (approved) {
            adminRequest.setStatus(Status.APPROVED);

            var user = adminRequest.getUser();
            user.setRole(Role.ROLE_ADMIN);
            userRepository.save(user);
        } else {
            adminRequest.setStatus(Status.REJECTED);
        }

        repository.save(adminRequest);
        eventService.notify(EventType.UPDATE, adminRequest);
        return mapper.map(adminRequest);
    }


    private User currentUser() {
        try {
            return userService.getCurrentUser();
        } catch (UsernameNotFoundException _ex) {
            return null;
        }
    }
}