package org.itmo.isLab1.coordinates;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.itmo.isLab1.common.framework.CrudController;
import org.itmo.isLab1.coordinates.dto.*;

@RestController
@RequestMapping(value = "/api/coordinates", produces = MediaType.APPLICATION_JSON_VALUE)
public class CoordinateController
    extends CrudController<
        Coordinate,
        CoordinateDto,
        CoordinateCreateDto,
        CoordinateUpdateDto,
        CoordinateService> {

    public CoordinateController(
        CoordinateService service
    ) {
        super(service);
    }
}
