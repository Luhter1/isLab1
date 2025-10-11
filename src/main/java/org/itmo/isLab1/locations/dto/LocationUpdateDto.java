package org.itmo.isLab1.locations.dto;

import lombok.Data;
import jakarta.validation.constraints.*;

import org.hibernate.validator.constraints.Length;
import org.openapitools.jackson.nullable.JsonNullable;

@Data
public class LocationUpdateDto {
    @NotNull
    private JsonNullable<Long> x;

    private JsonNullable<Integer> y;

    @NotNull
    private JsonNullable<Double> z;

    @NotNull
    @Length(max = 240)
    private JsonNullable<String> name;
}
