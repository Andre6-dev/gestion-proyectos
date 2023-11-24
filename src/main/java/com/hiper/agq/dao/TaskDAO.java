package com.hiper.agq.dao;

import com.hiper.agq.entity.Task;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskDAO {

    List<Task> selectAllTasks();

    Optional<Task> selectTaskById(UUID id);

    List<Task> selectTasksByProjectId(UUID projectId);

    List<Task> selectTasksByUserId(UUID userId);

    List<Task> selectTasksByProjectIdAndUserId(UUID projectId, UUID userId);

    Task insertTask(Task task);

    boolean existsTaskWithId(UUID id);

    void deleteTaskById(UUID id);

    Task updateTask(Task task);

}
