package com.hiper.agq.controller;

import com.hiper.agq.controller.common.ResponseHandler;
import com.hiper.agq.dto.AllTaskDto;
import com.hiper.agq.dto.CreateTaskDto;
import com.hiper.agq.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.hiper.agq.utils.Constants.API_BASE_PATH;

/**
 * andre on 23/11/2023
 */
@RestController
@RequestMapping(API_BASE_PATH + "tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<Object> getAllTasks() {
        return ResponseHandler.response(HttpStatus.OK, taskService.getAllTasks(), true);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<Object> getTasksByProjectId(@PathVariable("projectId") UUID projectId) {
        return ResponseHandler.response(HttpStatus.OK, taskService.getTasksByProjectId(projectId), true);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Object> getTasksByUserId(@PathVariable("userId") UUID userId) {
        return ResponseHandler.response(HttpStatus.OK, taskService.getTasksByUserId(userId), true);
    }

    @GetMapping("/project/{projectId}/user/{userId}")
    public ResponseEntity<Object> getTasksByProjectIdAndUserId(@PathVariable("projectId") UUID projectId, @PathVariable("userId") UUID userId) {
        return ResponseHandler.response(HttpStatus.OK, taskService.getTasksByProjectIdAndUserId(projectId, userId), true);
    }

    @PostMapping
    public ResponseEntity<Object> createTask(@RequestParam("projectId") UUID projectId, @RequestParam("userId") UUID userId, @RequestBody CreateTaskDto task) {
        return ResponseHandler.response(HttpStatus.OK, taskService.createTask(projectId, userId, task), true);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Object> deleteTask(@PathVariable("taskId") UUID taskId) {
        taskService.deleteTask(taskId);
        return ResponseHandler.response(HttpStatus.OK, "Task deleted successfully", true);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<Object> updateTask(@PathVariable("taskId") UUID taskId, @RequestBody CreateTaskDto task) {
        return ResponseHandler.response(HttpStatus.OK, taskService.updateTask(taskId, task), true);
    }

}
