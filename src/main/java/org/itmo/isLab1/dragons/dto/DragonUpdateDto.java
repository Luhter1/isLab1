package org.itmo.isLab1.dragons.dto;

import lombok.Data;
import jakarta.validation.constraints.*;
import org.openapitools.jackson.nullable.JsonNullable;
import org.itmo.isLab1.dragons.enums.DragonType;
import org.itmo.isLab1.dragons.enums.DragonCharacter;
import org.itmo.isLab1.people.enums.Color;


@Data
public class DragonUpdateDto {
    @NotNull
    @NotBlank
    private JsonNullable<String> name;

    @NotNull
    private JsonNullable<Integer> coordinatesId;

    private JsonNullable<Integer> caveId;

    private JsonNullable<Integer> killerId;

    @Min(0)
    private JsonNullable<Integer> age;

    private JsonNullable<Color> сolor;
  
    private JsonNullable<DragonType> type;

    @NotNull
    private JsonNullable<DragonCharacter> character;

    private JsonNullable<Integer> headId;
}