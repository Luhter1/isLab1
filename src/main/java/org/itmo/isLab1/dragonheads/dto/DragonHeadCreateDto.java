package org.itmo.isLab1.dragonheads.dto;

import org.itmo.isLab1.common.framework.dto.ClientDto;

import lombok.Data;

@Data
public class DragonHeadCreateDto implements ClientDto{
    private Float size;
}
