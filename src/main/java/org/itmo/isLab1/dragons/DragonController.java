package org.itmo.isLab1.dragons;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.itmo.isLab1.common.framework.CrudController;
import org.itmo.isLab1.dragons.dto.*;

@RestController
@RequestMapping(value = "/api/dragons", produces = MediaType.APPLICATION_JSON_VALUE)
public class DragonController
    extends CrudController<
        Dragon,
        DragonDto,
        DragonCreateDto,
        DragonUpdateDto,
        DragonService> {

    public DragonController(
        DragonService service
    ) {
        super(service);
    }
}
