package com.hiper.agq.dto;

import com.hiper.agq.entity.enums.TypeProject;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for {@link com.hiper.agq.entity.Project}
 */
public record AllProjectDto(UUID id, String name, String description, TypeProject type, LocalDateTime startDate,
                            LocalDateTime endDate, String logo) implements Serializable {
}