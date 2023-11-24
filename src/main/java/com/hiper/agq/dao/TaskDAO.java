package com.hiper.agq.dao;

import com.hiper.agq.entity.Task;

import java.util.List;

public interface TaskDAO {

    List<Task> selectAllTasks();

    List<Task> selectTasksByProjectId(Long projectId);

    List<Task> selectTasksByUserId(Long userId);

    List<Task> selectTasksByProjectIdAndUserId(Long projectId, Long userId);

    Task insertTask(Task task);

    boolean existsTaskWithId(Long id);

    void deleteTaskById(Long id);

    Task updateTask(Task task);

}
