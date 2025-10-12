package org.itmo.isLab1.dragons.mapper;

import org.mapstruct.*;
import org.itmo.isLab1.common.framework.mapper.CrudMapper;
import org.itmo.isLab1.common.mapper.JsonNullableMapper;
import org.itmo.isLab1.common.mapper.ReferenceMapper;
import org.itmo.isLab1.dragons.dto.*;
import org.itmo.isLab1.dragons.Dragon;

@Mapper(
    uses = { JsonNullableMapper.class, ReferenceMapper.class },
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class DragonMapper implements CrudMapper<Dragon, DragonDto, DragonCreateDto, DragonUpdateDto> {
    @Mapping(source = "coordinatesId", target = "coordinates")
    @Mapping(source = "caveId", target = "cave")
    @Mapping(source = "killerId", target = "killer")
    @Mapping(source = "headId", target = "head")
    public abstract Dragon map(DragonCreateDto dto);

    public abstract DragonDto map(Dragon model);

    public abstract Dragon map(DragonDto model);
    
    @Mapping(source = "coordinatesId", target = "coordinates")
    @Mapping(source = "caveId", target = "cave")
    @Mapping(source = "killerId", target = "killer")
    @Mapping(source = "headId", target = "head")
    public abstract void update(DragonUpdateDto dto, @MappingTarget Dragon model);
}