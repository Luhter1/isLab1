package org.itmo.isLab1.common.framework;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import org.itmo.isLab1.common.framework.dto.CrudDto;
import org.itmo.isLab1.common.framework.mapper.CrudMapper;
import org.itmo.isLab1.common.framework.policy.CrudPolicy;
import org.itmo.isLab1.common.errors.ResourceNotFoundException;
import org.itmo.isLab1.events.EventService;
import org.itmo.isLab1.events.EventType;
import org.itmo.isLab1.users.User;
import org.itmo.isLab1.users.UserService;

import java.time.ZonedDateTime;

@AllArgsConstructor
public abstract class CrudService<
    T extends CrudEntity,
    TRepository extends CrudRepository<T>,
    TMapper extends CrudMapper<T, TDto, TCreateDto, TUpdateDto>,
    TPolicy extends CrudPolicy<T>,
    TDto extends CrudDto,
    TCreateDto,
    TUpdateDto> {

    private TRepository repository;
    private TMapper mapper;
    private TPolicy policy;
    private UserService userService;
    private EventService<T> eventService;

    public Page<TDto> getAll(Pageable pageable) {
        policy.showAll(currentUser());

        var objs = repository.findAll(pageable);
        return objs.map(mapper::map);
    }

    @Transactional
    public TDto create(TCreateDto objData) {
        policy.create(currentUser());

        var obj = mapper.map(objData);
        obj.setCreatedBy(currentUser());
        obj.setCreatedAt(ZonedDateTime.now());
        repository.save(obj);
        eventService.notify(EventType.CREATE, obj);
        return mapper.map(obj);
    }

    public TDto getById(int id) {
        var obj = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not Found: " + id));
        policy.show(currentUser(), obj);

        return mapper.map(obj);
    }

    @Transactional
    public TDto update(TUpdateDto objData, int id) {
        var obj = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not Found: " + id));
        policy.update(currentUser(), obj);

        mapper.update(objData, obj);
        obj.setUpdatedBy(currentUser());
        obj.setUpdatedAt(ZonedDateTime.now());
        repository.save(obj);
        eventService.notify(EventType.UPDATE, obj);
        return mapper.map(obj);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public boolean delete(int id) {
        return repository.findById(id)
            .map(obj -> {
                policy.delete(currentUser(), obj);
                eventService.notify(EventType.DELETE, obj);
                repository.delete(obj);
                return true;
            }).orElse(false);
    }


    private User currentUser() {
        try {
            return userService.getCurrentUser();
        } catch (UsernameNotFoundException _ex) {
            return null;
        }
    }
}