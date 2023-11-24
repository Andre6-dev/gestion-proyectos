package com.hiper.agq.dto.mapper;

import com.hiper.agq.dto.AllProjectDto;
import com.hiper.agq.entity.Project;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AllProjectMapper {
    Project toEntity(AllProjectDto allProjectDto);

    AllProjectDto toDto(Project project);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Project partialUpdate(AllProjectDto allProjectDto, @MappingTarget Project project);
}