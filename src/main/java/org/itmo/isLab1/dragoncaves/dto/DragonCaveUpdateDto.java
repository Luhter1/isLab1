package org.itmo.isLab1.dragoncaves.dto;

import lombok.*;

import org.itmo.isLab1.common.framework.dto.ClientDto;
import org.openapitools.jackson.nullable.JsonNullable;

@Data
public class DragonCaveUpdateDto implements ClientDto{
    @NonNull
    private JsonNullable<Integer> depth;
}