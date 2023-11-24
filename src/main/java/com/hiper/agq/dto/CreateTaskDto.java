package com.hiper.agq.dto;

import com.hiper.agq.entity.Project;
import com.hiper.agq.entity.enums.PriorityEnum;
import com.hiper.agq.entity.enums.TaskStatus;
import com.hiper.agq.entity.enums.TypeTask;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for {@link com.hiper.agq.entity.Task}
 */
public record CreateTaskDto(UUID id, @NotNull String name,@NotNull String description, @NotNull TaskStatus status,
                            @NotNull PriorityEnum priority, @NotNull TypeTask type,
                            @NotNull LocalDateTime startDate, @NotNull LocalDateTime endDate, @NotNull String estimatedTime,
                            Project project) implements Serializable {
}