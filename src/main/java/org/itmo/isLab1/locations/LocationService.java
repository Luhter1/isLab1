package org.itmo.isLab1.locations;

import org.springframework.stereotype.Service;
import org.itmo.isLab1.common.errors.EntityDuplicateException;
import org.itmo.isLab1.common.framework.CrudService;
import org.itmo.isLab1.events.EventService;
import org.itmo.isLab1.locations.dto.*;
import org.itmo.isLab1.locations.mapper.LocationMapper;
import org.itmo.isLab1.locations.policy.LocationPolicy;
import org.itmo.isLab1.users.UserService;

@Service
public class LocationService
    extends CrudService<
        Location,
        LocationRepository,
        LocationMapper,
        LocationPolicy,
        LocationDto,
        LocationCreateDto,
        LocationUpdateDto> {

    public LocationService(
        LocationRepository repository,
        LocationMapper mapper,
        LocationPolicy policy,
        UserService userService,
        EventService<Location> eventService
    ) {
        super(repository, mapper, policy, userService, eventService);
    }

    @Override
    protected void checkUniqueness(Location obj){
        repository.lockByLocation(obj.getX().toString(), obj.getY().toString(), obj.getZ().toString(), obj.getName());
        if(repository.existsByXAndYAndZAndNameAndIdNot(obj.getX(), obj.getY(), obj.getZ(), obj.getName(), obj.getId())){
            throw new EntityDuplicateException("location is not uniq");
        }
    };

}