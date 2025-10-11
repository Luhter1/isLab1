package org.itmo.isLab1.coordinates;

import org.itmo.isLab1.coordinates.dto.*;
import org.itmo.isLab1.coordinates.mapper.CoordinateMapper;
import org.itmo.isLab1.coordinates.policy.CoordinatePolicy;
import org.springframework.stereotype.Service;
import org.itmo.isLab1.common.framework.CrudService;
import org.itmo.isLab1.events.EventService;
import org.itmo.isLab1.users.UserService;

@Service
public class CoordinateService
    extends CrudService<
        Coordinate,
        CoordinateRepository,
        CoordinateMapper,
        CoordinatePolicy,
        CoordinateDto,
        CoordinateCreateDto,
        CoordinateUpdateDto> {

    public CoordinateService(
        CoordinateRepository repository,
        CoordinateMapper mapper,
        CoordinatePolicy policy,
        UserService userService,
        EventService<Coordinate> eventService
    ) {
        super(repository, mapper, policy, userService, eventService);
    }
}