package org.itmo.isLab1.dragonheads.dto;

import lombok.Data;
import org.openapitools.jackson.nullable.JsonNullable;

@Data
public class DragonHeadUpdateDto {
    private JsonNullable<Float> size;
}