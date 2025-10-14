package org.itmo.isLab1.locations.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LocationCreateDto {
    @NotNull
    private Long x;

    private Integer y;

    @NotNull
    private Double z;

    @NotNull
    @Length(max = 240)
    private String name;
}
