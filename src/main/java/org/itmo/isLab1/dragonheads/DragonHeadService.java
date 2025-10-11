package org.itmo.isLab1.dragonheads;

import org.springframework.stereotype.Service;
import org.itmo.isLab1.common.framework.CrudService;
import org.itmo.isLab1.dragonheads.dto.*;
import org.itmo.isLab1.dragonheads.mapper.DragonHeadMapper;
import org.itmo.isLab1.dragonheads.policy.DragonHeadPolicy;
import org.itmo.isLab1.events.EventService;
import org.itmo.isLab1.users.UserService;

@Service
public class DragonHeadService
    extends CrudService<
        DragonHead,
        DragonHeadRepository,
        DragonHeadMapper,
        DragonHeadPolicy,
        DragonHeadDto,
        DragonHeadCreateDto,
        DragonHeadUpdateDto> {

    public DragonHeadService(
        DragonHeadRepository repository,
        DragonHeadMapper mapper,
        DragonHeadPolicy policy,
        UserService userService,
        EventService<DragonHead> eventService
    ) {
        super(repository, mapper, policy, userService, eventService);
    }
}
