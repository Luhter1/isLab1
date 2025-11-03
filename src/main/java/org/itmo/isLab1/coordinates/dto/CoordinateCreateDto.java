package org.itmo.isLab1.coordinates.dto;

import org.itmo.isLab1.common.framework.dto.ClientDto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CoordinateCreateDto implements ClientDto{
    @NotNull
    @Min(-998)
    private Integer x;

    @NotNull
    @Max(844)
    private Double y;
}