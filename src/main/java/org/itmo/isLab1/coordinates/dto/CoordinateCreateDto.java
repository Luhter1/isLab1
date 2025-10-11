package org.itmo.isLab1.coordinates.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CoordinateCreateDto {
    @NotNull
    @Min(-998)
    private Integer x;

    @NotNull
    @Max(844)
    private Float y;
}