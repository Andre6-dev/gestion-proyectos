package com.hiper.agq.service;

import com.hiper.agq.dao.ProjectDAO;
import com.hiper.agq.dao.TaskDAO;
import com.hiper.agq.dao.UserDAO;
import com.hiper.agq.dto.AllTaskDto;
import com.hiper.agq.dto.CreateTaskDto;
import com.hiper.agq.dto.mapper.AllTaskMapper;
import com.hiper.agq.dto.mapper.CreateTaskMapper;
import com.hiper.agq.entity.Project;
import com.hiper.agq.entity.Task;
import com.hiper.agq.entity.User;
import com.hiper.agq.exception.common.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * andre on 23/11/2023
 */
@Slf4j(topic = "TaskService")
@Service
public class TaskService {

    private final TaskDAO taskDAO;

    private final AllTaskMapper allTaskMapper;

    private final CreateTaskMapper createTaskMapper;

    private final UserDAO userDAO;

    private final ProjectDAO projectDAO;

    public TaskService(@Qualifier("taskDAO") TaskDAO taskDAO, AllTaskMapper allTaskMapper, CreateTaskMapper createTaskMapper, UserDAO userDAO, ProjectDAO projectDAO) {
        this.taskDAO = taskDAO;
        this.allTaskMapper = allTaskMapper;
        this.createTaskMapper = createTaskMapper;
        this.userDAO = userDAO;
        this.projectDAO = projectDAO;
    }

    public List<AllTaskDto> getAllTasks() {
        log.info("Getting all tasks");
        return taskDAO.selectAllTasks()
                .stream()
                .map(allTaskMapper::toDto)
                .toList();
    }

    public List<AllTaskDto> getTasksByProjectId(UUID projectId) {
        log.info("Getting tasks by project id [{}]", projectId);
        return taskDAO.selectTasksByProjectId(projectId)
                .stream()
                .map(allTaskMapper::toDto)
                .toList();
    }

    public List<AllTaskDto> getTasksByUserId(UUID userId) {
        log.info("Getting tasks by user id [{}]", userId);
        return taskDAO.selectTasksByUserId(userId)
                .stream()
                .map(allTaskMapper::toDto)
                .toList();
    }

    public List<AllTaskDto> getTasksByProjectIdAndUserId(UUID projectId, UUID userId) {
        log.info("Getting tasks by project id [{}] and user id [{}]", projectId, userId);
        return taskDAO.selectTasksByProjectIdAndUserId(projectId, userId)
                .stream()
                .map(allTaskMapper::toDto)
                .toList();
    }

    public List<CreateTaskDto> getTaskById(UUID taskId) {
        log.info("Getting task by id [{}]", taskId);
        return taskDAO.selectTaskById(taskId)
                .stream()
                .map(createTaskMapper::toDto)
                .toList();
    }

    public CreateTaskDto createTask(UUID projectId, UUID userId, CreateTaskDto createTaskDto) {
        log.info("Creating task with name [{}]", createTaskDto.name());

        Project project = projectDAO.selectProjectById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project with id [%s] not found".formatted(projectId)));


        User user = userDAO.selectUserById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id [%s] not found".formatted(userId)));

        Task newTask = createTaskMapper.toEntity(createTaskDto);

        // Assign project to task
        newTask.setProject(project);

        // Assign user to task
        newTask.assignUserToTask(user);

        newTask = taskDAO.insertTask(newTask);

        return createTaskMapper.toDto(newTask);
    }

    public void deleteTask(UUID taskId) {
        log.info("Deleting task with id [{}]", taskId);

        if (!taskDAO.existsTaskWithId(taskId)) {
            throw new ResourceNotFoundException("Task with id [%s] does not exists".formatted(taskId));
        }

        // Remove user from task
        User user = userDAO.selectUserById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("User with task id [%s] not found".formatted(taskId)));

        Task task = taskDAO.selectTaskById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id [%s] not found".formatted(taskId)));

        task.removeUserFromTask(user);

        taskDAO.deleteTaskById(taskId);
    }

    public CreateTaskDto updateTask(UUID taskId, CreateTaskDto createTaskDto) {
        log.info("Updating task with id [{}]", taskId);

        Task task = createTaskMapper.toEntity(createTaskDto);

        //
        boolean changes = false;

        if (createTaskDto.name() != null && !createTaskDto.name().equals(task.getName())) {
            task.setName(createTaskDto.name());
            changes = true;
        }

        if (createTaskDto.description() != null && !createTaskDto.description().equals(task.getDescription())) {
            task.setDescription(createTaskDto.description());
            changes = true;
        }

        if (createTaskDto.status() != null && !createTaskDto.status().equals(task.getStatus())) {
            task.setStatus(createTaskDto.status());
            changes = true;
        }

        if (createTaskDto.priority() != null && !createTaskDto.priority().equals(task.getPriority())) {
            task.setPriority(createTaskDto.priority());
            changes = true;
        }

        if (createTaskDto.type() != null && !createTaskDto.type().equals(task.getType())) {
            task.setType(createTaskDto.type());
            changes = true;
        }

        if (createTaskDto.startDate() != null && !createTaskDto.startDate().equals(task.getStartDate())) {
            task.setStartDate(createTaskDto.startDate());
            changes = true;
        }

        if (createTaskDto.endDate() != null && !createTaskDto.endDate().equals(task.getEndDate())) {
            task.setEndDate(createTaskDto.endDate());
            changes = true;
        }

        if (createTaskDto.estimatedTime() != null && !createTaskDto.estimatedTime().equals(task.getEstimatedTime())) {
            task.setEstimatedTime(createTaskDto.estimatedTime());
            changes = true;
        }

        if (!changes) {
            throw new RuntimeException("No changes were made");
        }

        return createTaskMapper.toDto(taskDAO.updateTask(task));

    }

}
