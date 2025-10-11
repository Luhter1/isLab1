package org.itmo.isLab1.dragoncaves.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.itmo.isLab1.common.framework.dto.CrudDto;

@Data
@EqualsAndHashCode(callSuper = true)
public class DragonCaveDto extends CrudDto {
    private int id;
    private Integer depth;
}