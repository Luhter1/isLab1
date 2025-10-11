package org.itmo.isLab1.locations.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.itmo.isLab1.common.framework.dto.CrudDto;

@Data
@EqualsAndHashCode(callSuper = true)
public class LocationDto extends CrudDto {
    private int id;
    private Long x;
    private int y;
    private Double z;
    private String name;
}
