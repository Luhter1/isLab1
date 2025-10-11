package org.itmo.isLab1.dragoncaves;

import org.springframework.stereotype.Service;
import org.itmo.isLab1.common.framework.CrudService;
import org.itmo.isLab1.dragoncaves.dto.*;
import org.itmo.isLab1.dragoncaves.mapper.DragonCaveMapper;
import org.itmo.isLab1.dragoncaves.policy.DragonCavePolicy;
import org.itmo.isLab1.events.EventService;
import org.itmo.isLab1.users.UserService;

@Service
public class DragonCaveService
    extends CrudService<
        DragonCave,
        DragonCaveRepository,
        DragonCaveMapper,
        DragonCavePolicy,
        DragonCaveDto,
        DragonCaveCreateDto,
        DragonCaveUpdateDto> {

    public DragonCaveService(
        DragonCaveRepository repository,
        DragonCaveMapper mapper,
        DragonCavePolicy policy,
        UserService userService,
        EventService<DragonCave> eventService
    ) {
        super(repository, mapper, policy, userService, eventService);
    }
}
