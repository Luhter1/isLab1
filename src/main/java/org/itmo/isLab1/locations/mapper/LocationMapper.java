package org.itmo.isLab1.locations.mapper;

import org.mapstruct.*;
import org.itmo.isLab1.common.framework.mapper.CrudMapper;
import org.itmo.isLab1.common.mapper.*;
import org.itmo.isLab1.locations.dto.*;
import org.itmo.isLab1.locations.Location;

@Mapper(
    uses = { JsonNullableMapper.class, ReferenceMapper.class },
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class LocationMapper implements CrudMapper<Location, LocationDto, LocationCreateDto, LocationUpdateDto> {
    public abstract Location map(LocationCreateDto dto);
    public abstract LocationDto map(Location model);
    public abstract Location map(LocationDto model);
    public abstract void update(LocationUpdateDto dto, @MappingTarget Location model);
}
