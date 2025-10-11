package org.itmo.isLab1.people.dto;

import lombok.Data;
import jakarta.validation.constraints.*;
import org.openapitools.jackson.nullable.JsonNullable;
import org.hibernate.validator.constraints.Length;
import org.itmo.isLab1.people.enums.Color;

import java.time.LocalDateTime;

@Data
public class PersonUpdateDto {
    @NotNull
    @NotBlank
    private JsonNullable<String> name;

    private JsonNullable<Color> eyeColor;
    
    @NotNull
    private JsonNullable<Color> hairColor;

    private JsonNullable<Integer> locationId;

    @Past
    private JsonNullable<LocalDateTime> birthday;

    @NotNull
    @Min(0)
    private JsonNullable<Float> height;

    @Min(0)
    private JsonNullable<Integer> weight;

    @NotNull
    @NotBlank
    @Length(max = 23)
    private JsonNullable<String> passportId;
}