package org.itmo.isLab1.people.mapper;

import org.mapstruct.*;

import org.itmo.isLab1.common.framework.mapper.CrudMapper;
import org.itmo.isLab1.common.mapper.JsonNullableMapper;
import org.itmo.isLab1.common.mapper.ReferenceMapper;
import org.itmo.isLab1.people.dto.*;
import org.itmo.isLab1.people.Person;

@Mapper(
    uses = { JsonNullableMapper.class, ReferenceMapper.class },
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class PersonMapper implements CrudMapper<Person, PersonDto, PersonCreateDto, PersonUpdateDto> {
    @Mapping(source = "locationId", target = "location")
    public abstract Person map(PersonCreateDto dto);
    public abstract PersonDto map(Person model);
    public abstract Person map(PersonDto model);
    public abstract void update(PersonUpdateDto dto, @MappingTarget Person model);
}
