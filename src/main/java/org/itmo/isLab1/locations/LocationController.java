package org.itmo.isLab1.locations;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.itmo.isLab1.common.framework.CrudController;
import org.itmo.isLab1.locations.dto.*;

@RestController
@RequestMapping(value = "/api/locations", produces = MediaType.APPLICATION_JSON_VALUE)
public class LocationController
    extends CrudController<
        Location,
        LocationDto,
        LocationCreateDto,
        LocationUpdateDto,
        LocationService> {

    public LocationController(
        LocationService service
    ) {
        super(service);
    }
}
