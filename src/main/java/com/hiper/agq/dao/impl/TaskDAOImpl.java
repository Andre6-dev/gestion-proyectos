package com.hiper.agq.dao.impl;

import com.hiper.agq.dao.TaskDAO;
import com.hiper.agq.entity.Task;
import com.hiper.agq.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * andre on 23/11/2023
 */
@Repository("taskDAO")
@RequiredArgsConstructor
public class TaskDAOImpl implements TaskDAO {

    private final TaskRepository taskRepository;

    @Override
    public List<Task> selectAllTasks() {
        return null;
    }

    @Override
    public List<Task> selectTasksByProjectId(Long projectId) {
        return null;
    }

    @Override
    public List<Task> selectTasksByUserId(Long userId) {
        return null;
    }

    @Override
    public List<Task> selectTasksByProjectIdAndUserId(Long projectId, Long userId) {
        return null;
    }

    @Override
    public Task insertTask(Task task) {
        return null;
    }

    @Override
    public boolean existsTaskWithId(Long id) {
        return false;
    }

    @Override
    public void deleteTaskById(Long id) {

    }

    @Override
    public Task updateTask(Task task) {
        return null;
    }
}
