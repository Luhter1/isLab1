package org.itmo.isLab1.people.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import org.hibernate.validator.constraints.Length;
import org.itmo.isLab1.common.framework.dto.ClientDto;
import org.itmo.isLab1.people.enums.Color;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

@Data
public class PersonCreateDto implements ClientDto{
    @NotNull
    @NotBlank
    private String name;

    private Color eyeColor;
    
    @NotNull
    private Color hairColor;

    private Integer locationId;

    @Past
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
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
