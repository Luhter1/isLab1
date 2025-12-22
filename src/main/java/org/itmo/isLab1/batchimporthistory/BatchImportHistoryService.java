package org.itmo.isLab1.batchimporthistory;

import lombok.AllArgsConstructor;
import org.itmo.isLab1.batchimporthistory.dto.BatchImportHistoryDto;
import org.itmo.isLab1.batchimporthistory.enums.ImportStatus;
import org.itmo.isLab1.events.EventService;
import org.itmo.isLab1.events.EventType;
import org.itmo.isLab1.users.User;
import org.itmo.isLab1.users.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class BatchImportHistoryService {
    private final BatchImportHistoryRepository repository;
    private final UserService userService;
    private EventService<BatchImportHistory> eventService;

    public Page<BatchImportHistoryDto> getHistory(Pageable pageable, boolean isAdmin) {
        Page<BatchImportHistory> history;
        
        if (isAdmin) {
            // Администратор видит всю историю
            history = repository.findAllByOrderByCreatedAtDesc(pageable);
        } else {
            // Обычный пользователь видит только свои операции
            User currentUser = userService.getCurrentUser();
            history = repository.findByCreatedByOrderByCreatedAtDesc(currentUser, pageable);
        }
        
        return history.map(this::mapToDto);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveImportHistory(int successfulOperations, ImportStatus status) {
        var currentUser = userService.getCurrentUser();
        var history = BatchImportHistory.builder()
                .createdBy(currentUser)
                .status(status)
                .successfulOperations(successfulOperations)
                .build();
        repository.save(history);
        eventService.notify(EventType.CREATE, history);
    }

    private BatchImportHistoryDto mapToDto(BatchImportHistory history) {
        return new BatchImportHistoryDto(
                history.getId(),
                history.getCreatedBy(),
                history.getCreatedAt(),
                history.getStatus(),
                history.getSuccessfulOperations()
        );
    }
}