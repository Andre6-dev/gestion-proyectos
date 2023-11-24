package com.hiper.agq.dto;

import com.hiper.agq.entity.Project;
import com.hiper.agq.entity.enums.TypeProject;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link Project}
 */
public record ProjectDto(@NotNull String name, String description, @NotNull TypeProject type,
                         @NotNull LocalDateTime startDate, @NotNull LocalDateTime endDate) implements Serializable {
}