package org.itmo.isLab1.coordinates.dto;

import lombok.Data;
import jakarta.validation.constraints.*;
import org.openapitools.jackson.nullable.JsonNullable;

@Data
public class CoordinateUpdateDto {
    @NotNull
    @Min(-998)
    private JsonNullable<Integer> x;

    @NotNull
    @Max(844)
    private JsonNullable<Double> y;
}