package com.hiper.agq.dao.impl;

import com.hiper.agq.dao.TaskDAO;
import com.hiper.agq.entity.Task;
import com.hiper.agq.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * andre on 23/11/2023
 */
@Repository("taskDAO")
@RequiredArgsConstructor
public class TaskDAOImpl implements TaskDAO {

    private final TaskRepository taskRepository;

    @Override
    public List<Task> selectAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Optional<Task> selectTaskById(UUID id) {
        return taskRepository.findById(id);
    }

    @Override
    public List<Task> selectTasksByProjectId(UUID projectId) {
        return taskRepository.findByProject_Id(projectId);
    }

    @Override
    public List<Task> selectTasksByUserId(UUID userId) {
        return taskRepository.findByUsers_Id(userId);
    }

    @Override
    public List<Task> selectTasksByProjectIdAndUserId(UUID projectId, UUID userId) {
        return taskRepository.findByProject_IdAndUsers_Id(projectId, userId);
    }

    @Override
    public Task insertTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public boolean existsTaskWithId(UUID id) {
        return taskRepository.existsTaskById(id);
    }

    @Override
    public void deleteTaskById(UUID id) {
        taskRepository.deleteById(id);
    }

    @Override
    public Task updateTask(Task task) {
        return taskRepository.save(task);
    }
}
