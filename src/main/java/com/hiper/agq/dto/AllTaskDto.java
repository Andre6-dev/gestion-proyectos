package com.hiper.agq.dto;

import com.hiper.agq.entity.enums.PriorityEnum;
import com.hiper.agq.entity.enums.TaskStatus;
import com.hiper.agq.entity.enums.TypeTask;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for {@link com.hiper.agq.entity.Task}
 */
public record AllTaskDto(UUID id, String name, String description, TaskStatus status, PriorityEnum priority,
                         TypeTask type, LocalDateTime startDate, LocalDateTime endDate,
                         String estimatedTime) implements Serializable {
}