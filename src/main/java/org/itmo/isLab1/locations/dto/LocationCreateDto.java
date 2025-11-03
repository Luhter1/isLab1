package org.itmo.isLab1.locations.dto;

import org.hibernate.validator.constraints.Length;
import org.itmo.isLab1.common.framework.dto.ClientDto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LocationCreateDto implements ClientDto{
    @NotNull
    private Long x;

    private Integer y;

    @NotNull
    private Double z;

    @NotNull
    @Length(max = 240)
    private String name;
}
