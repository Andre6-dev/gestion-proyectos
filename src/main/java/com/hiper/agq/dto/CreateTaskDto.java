package com.hiper.agq.dto;

import com.hiper.agq.entity.Project;
import com.hiper.agq.entity.enums.PriorityEnum;
import com.hiper.agq.entity.enums.TaskStatus;
import com.hiper.agq.entity.enums.TypeTask;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.hiper.agq.entity.Task}
 */
public record CreateTaskDto(String name, String description, TaskStatus status, PriorityEnum priority, TypeTask type,
                            LocalDateTime startDate, LocalDateTime endDate, String estimatedTime,
                            Project project) implements Serializable {
}