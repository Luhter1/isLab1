package org.itmo.isLab1.dragoncaves.dto;

import org.itmo.isLab1.common.framework.dto.ClientDto;

import lombok.*;

@Data
public class DragonCaveCreateDto implements ClientDto{
    @NonNull
    private Integer depth;
}
