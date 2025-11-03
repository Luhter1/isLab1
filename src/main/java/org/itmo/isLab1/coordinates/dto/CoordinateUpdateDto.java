package org.itmo.isLab1.coordinates.dto;

import lombok.Data;
import jakarta.validation.constraints.*;
import org.openapitools.jackson.nullable.JsonNullable;

import org.itmo.isLab1.common.framework.dto.ClientDto;

@Data
public class CoordinateUpdateDto implements ClientDto{
    @NotNull
    @Min(-998)
    private JsonNullable<Integer> x;

    @NotNull
    @Max(844)
    private JsonNullable<Double> y;
}