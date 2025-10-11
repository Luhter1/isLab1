package org.itmo.isLab1.common.framework.mapper;

import org.mapstruct.*;
import org.itmo.isLab1.common.framework.dto.CrudDto;
import org.itmo.isLab1.common.framework.CrudEntity;

public interface CrudMapper<T extends CrudEntity, TDto extends CrudDto, TCreateDto, TUpdateDto> {
    T map(TCreateDto dto);
    TDto map(T model);
    T map(TDto dto);
    void update(TUpdateDto dto, @MappingTarget T model);
}
