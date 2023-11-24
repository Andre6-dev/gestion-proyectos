package com.hiper.agq.dto.mapper;

import com.hiper.agq.dto.AllTaskDto;
import com.hiper.agq.entity.Task;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AllTaskMapper {
    Task toEntity(AllTaskDto allTaskDto);

    AllTaskDto toDto(Task task);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Task partialUpdate(AllTaskDto allTaskDto, @MappingTarget Task task);
}