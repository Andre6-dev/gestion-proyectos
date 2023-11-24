package com.hiper.agq.dto;

import com.hiper.agq.entity.Project;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link Project}
 */
public record ProjectDto(@NotNull String name, String description, @NotNull String type,
                         @NotNull LocalDateTime startDate, @NotNull LocalDateTime endDate) implements Serializable {
}