package org.itmo.isLab1.dragons;

import org.springframework.stereotype.Service;
import org.itmo.isLab1.common.framework.CrudService;
import org.itmo.isLab1.dragons.dto.*;
import org.itmo.isLab1.dragons.mapper.DragonMapper;
import org.itmo.isLab1.dragons.policy.DragonPolicy;
import org.itmo.isLab1.events.EventService;
import org.itmo.isLab1.users.UserService;

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
}