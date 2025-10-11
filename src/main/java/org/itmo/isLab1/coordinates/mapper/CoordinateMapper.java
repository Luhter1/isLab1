package org.itmo.isLab1.coordinates.mapper;

import org.mapstruct.*;
import org.itmo.isLab1.common.framework.mapper.CrudMapper;
import org.itmo.isLab1.common.mapper.*;
import org.itmo.isLab1.coordinates.dto.*;
import org.itmo.isLab1.coordinates.Coordinate;

@Mapper(
    uses = { JsonNullableMapper.class, ReferenceMapper.class },
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class CoordinateMapper implements CrudMapper<Coordinate, CoordinateDto, CoordinateCreateDto, CoordinateUpdateDto> {
    public abstract Coordinate map(CoordinateCreateDto dto);
    public abstract CoordinateDto map(Coordinate model);
    public abstract Coordinate map(CoordinateDto model);
    public abstract void update(CoordinateUpdateDto dto, @MappingTarget Coordinate model);
}