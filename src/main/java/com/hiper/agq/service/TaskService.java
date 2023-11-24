package com.hiper.agq.service;

import com.hiper.agq.dao.ProjectDAO;
import com.hiper.agq.dao.TaskDAO;
import com.hiper.agq.dao.UserDAO;
import com.hiper.agq.dto.AllTaskDto;
import com.hiper.agq.dto.CreateTaskDto;
import com.hiper.agq.dto.mapper.AllTaskMapper;
import com.hiper.agq.dto.mapper.CreateTaskMapper;
import com.hiper.agq.dto.mapper.UserMapper;
import com.hiper.agq.entity.Project;
import com.hiper.agq.entity.Task;
import com.hiper.agq.entity.User;
import com.hiper.agq.entity.enums.PriorityEnum;
import com.hiper.agq.entity.enums.TaskStatus;
import com.hiper.agq.entity.enums.TypeTask;
import com.hiper.agq.exception.common.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

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

    private final UserMapper userMapper;

    private final ProjectDAO projectDAO;

    public TaskService(@Qualifier("taskDAO") TaskDAO taskDAO, AllTaskMapper allTaskMapper, CreateTaskMapper createTaskMapper, UserDAO userDAO, UserMapper userMapper, ProjectDAO projectDAO) {
        this.taskDAO = taskDAO;
        this.allTaskMapper = allTaskMapper;
        this.createTaskMapper = createTaskMapper;
        this.userDAO = userDAO;
        this.userMapper = userMapper;
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
        List<User> users = userDAO.selectUserByTaskId(taskId);

        Task task = taskDAO.selectTaskById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id [%s] not found".formatted(taskId)));

        // Iterate users and remove task from user
        for (User user : users) {
            task.removeUserFromTask(user);
        }

        taskDAO.deleteTaskById(taskId);
    }

    public CreateTaskDto updateTask(UUID taskId, UUID userId, UUID projectId, CreateTaskDto updateTaskDto) {
        log.info("Updating task with id [{}]", taskId);

        Task task = taskDAO.selectTaskById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id [%s] not found".formatted(taskId)));

        Project project = projectDAO.selectProjectById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project with id [%s] not found".formatted(projectId)));

        User user = userDAO.selectUserById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id [%s] not found".formatted(userId)));

        updateTaskFromDto(task, updateTaskDto, project);

        // Assign user to task
        task.assignUserToTask(user);

        // Assign task to Project
        task.setProject(project);

        return createTaskMapper.toDto(taskDAO.updateTask(task));

    }

    public void updateTaskFromDto(Task task, CreateTaskDto updateTaskDto, Project project) {
        updateFieldIfNotNull(updateTaskDto.name(), task.getName(), task::setName);
        updateFieldIfNotNull(updateTaskDto.description(), task.getDescription(), task::setDescription);
        updateFieldIfNotNullEnum(updateTaskDto.status().toString(), task.getStatus(), TaskStatus::valueOf, task::setStatus);
        updateFieldIfNotNullEnum(updateTaskDto.priority().toString(), task.getPriority(), PriorityEnum::valueOf, task::setPriority);
        updateFieldIfNotNullEnum(updateTaskDto.type().toString(), task.getType(), TypeTask::valueOf, task::setType);
        updateFieldIfNotNull(updateTaskDto.startDate(), task.getStartDate(), task::setStartDate);
        updateFieldIfNotNull(updateTaskDto.endDate(), task.getEndDate(), task::setEndDate);
        updateFieldIfNotNull(updateTaskDto.estimatedTime(), task.getEstimatedTime(), task::setEstimatedTime);
        updateFieldIfNotNull(project, task.getProject(), task::setProject);
    }

    private <T> void updateFieldIfNotNull(T newValue, T currentValue, Consumer<T> updater) {
        if (Objects.nonNull(newValue) && !newValue.equals(currentValue)) {
            updater.accept(newValue);
        }
    }

    private <E extends Enum<E>> void updateFieldIfNotNullEnum(
            String newValue,
            E currentValue,
            Function<String, E> valueOfFunction,
            Consumer<E> updater) {
        if (Objects.nonNull(newValue)) {
            E newEnum = valueOfFunction.apply(newValue);
            if (!newEnum.equals(currentValue)) {
                updater.accept(newEnum);
            }
        }
    }

}
