package org.itmo.isLab1.dragonheads.dto;

import lombok.Data;

import org.itmo.isLab1.common.framework.dto.ClientDto;
import org.openapitools.jackson.nullable.JsonNullable;

@Data
public class DragonHeadUpdateDto implements ClientDto{
    private JsonNullable<Float> size;
}