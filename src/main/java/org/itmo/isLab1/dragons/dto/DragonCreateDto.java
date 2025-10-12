package org.itmo.isLab1.dragons.dto;


import jakarta.validation.constraints.*;
import lombok.Data;

import org.itmo.isLab1.dragons.enums.DragonType;
import org.itmo.isLab1.dragons.enums.DragonCharacter;
import org.itmo.isLab1.people.enums.Color;


@Data
public class DragonCreateDto {
    @NotNull
    @NotBlank
    private String name;

    @NotNull
    private Integer coordinatesId;

    private Integer caveId;

    private Integer killerId;

    @Min(0)
    private Integer age;

    private Color сolor;
  
    private DragonType type;

    @NotNull
    private DragonCharacter character;

    private Integer headId;
}
