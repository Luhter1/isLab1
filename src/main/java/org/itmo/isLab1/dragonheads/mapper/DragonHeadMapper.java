package org.itmo.isLab1.dragonheads.mapper;

import org.mapstruct.*;
import org.itmo.isLab1.common.framework.mapper.CrudMapper;
import org.itmo.isLab1.common.mapper.*;
import org.itmo.isLab1.dragonheads.dto.*;
import org.itmo.isLab1.dragonheads.DragonHead;

@Mapper(
    uses = { JsonNullableMapper.class, ReferenceMapper.class },
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class DragonHeadMapper implements CrudMapper<DragonHead, DragonHeadDto, DragonHeadCreateDto, DragonHeadUpdateDto> {
    public abstract DragonHead map(DragonHeadCreateDto dto);
    public abstract DragonHeadDto map(DragonHead model);
    public abstract DragonHead map(DragonHeadDto model);
    public abstract void update(DragonHeadUpdateDto dto, @MappingTarget DragonHead model);
}
