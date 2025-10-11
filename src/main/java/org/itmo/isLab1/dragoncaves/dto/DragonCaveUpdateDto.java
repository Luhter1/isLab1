package org.itmo.isLab1.dragoncaves.dto;

import lombok.Data;
import org.openapitools.jackson.nullable.JsonNullable;

@Data
public class DragonCaveUpdateDto {
    private JsonNullable<Integer> depth;
}