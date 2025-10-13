package org.itmo.isLab1.people.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import org.hibernate.validator.constraints.Length;
import org.itmo.isLab1.people.enums.Color;

import java.time.LocalDateTime;

@Data
public class PersonCreateDto {
    @NotNull
    @NotBlank
    private String name;

    private Color eyeColor;
    
    @NotNull
    private Color hairColor;

    private Integer locationId;

    @Past
    private LocalDateTime birthday;

    @NotNull
    @Min(0)
    private Float height;

    @Min(0)
    private Integer weight;

    @NotNull
    @NotBlank
    @Length(max = 23)
    private String passportId;
}
