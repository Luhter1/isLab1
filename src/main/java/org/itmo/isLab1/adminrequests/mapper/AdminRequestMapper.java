package org.itmo.isLab1.adminrequests.mapper;

import org.mapstruct.*;
import org.itmo.isLab1.adminrequests.dto.AdminRequestDto;
import org.itmo.isLab1.adminrequests.AdminRequest;
import org.itmo.isLab1.common.mapper.JsonNullableMapper;
import org.itmo.isLab1.common.mapper.ReferenceMapper;

@Mapper(
  uses = { JsonNullableMapper.class, ReferenceMapper.class },
  nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
  componentModel = MappingConstants.ComponentModel.SPRING,
  unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class AdminRequestMapper {
  public abstract AdminRequestDto map(AdminRequest model);
  public abstract AdminRequest map(AdminRequestDto model);
}