package org.itmo.isLab1.dragoncaves.dto;

import lombok.*;
import org.openapitools.jackson.nullable.JsonNullable;

@Data
public class DragonCaveUpdateDto {
    @NonNull
    private JsonNullable<Integer> depth;
}