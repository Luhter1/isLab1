package org.itmo.isLab1.people.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.itmo.isLab1.common.framework.dto.CrudDto;
import org.itmo.isLab1.locations.Location;
import org.itmo.isLab1.people.enums.Color;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class PersonDto extends CrudDto {
    private int id;
    private String name;
    private Color eyeColor;
    private Color hairColor;
    private Location location;
    private LocalDateTime birthday;
    private Float height;
    private Integer weight;
    private String passportId;
}
