package com.hiper.agq.dto.mapper;

import com.hiper.agq.dto.CreateTaskDto;
import com.hiper.agq.entity.Task;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CreateTaskMapper {
    Task toEntity(CreateTaskDto createTaskDto);

    CreateTaskDto toDto(Task task);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Task partialUpdate(CreateTaskDto createTaskDto, @MappingTarget Task task);
}