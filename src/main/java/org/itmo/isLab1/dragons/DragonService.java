package org.itmo.isLab1.dragons;

import org.springframework.stereotype.Service;
import org.itmo.isLab1.common.framework.CrudService;
import org.itmo.isLab1.dragons.dto.*;
import org.itmo.isLab1.dragons.mapper.DragonMapper;
import org.itmo.isLab1.dragons.policy.DragonPolicy;
import org.itmo.isLab1.events.EventService;
import org.itmo.isLab1.users.UserService;
import org.itmo.isLab1.common.errors.EntityDuplicateException;

@Service
public class DragonService
    extends CrudService<
        Dragon,
        DragonRepository,
        DragonMapper,
        DragonPolicy,
        DragonDto,
        DragonCreateDto,
        DragonUpdateDto> {

    public DragonService(
        DragonRepository repository,
        DragonMapper mapper,
        DragonPolicy policy,
        UserService userService,
        EventService<Dragon> eventService
    ) {
        super(repository, mapper, policy, userService, eventService);
    }

    @Override
    protected void checkUniqueness(Dragon obj) {
        if(obj.getHead() == null) return;
        Integer headId = obj.getHead().getId();

        if(repository.existsByHead_IdAndIdNot(headId, obj.getId())){
            throw new EntityDuplicateException("headId is not uniq");
        }
    }

}