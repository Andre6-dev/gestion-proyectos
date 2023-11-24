package com.hiper.agq.controller;


import com.hiper.agq.controller.common.ResponseHandler;
import com.hiper.agq.dto.AllProjectDto;
import com.hiper.agq.dto.ProjectDto;
import com.hiper.agq.entity.enums.TypeProject;
import com.hiper.agq.service.ProjectService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.hiper.agq.utils.Constants.API_BASE_PATH;

/**
 * andre on 23/11/2023
 */
@Tag(name = "Project", description = "Project API")
@RestController
@RequestMapping(API_BASE_PATH + "projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public ResponseEntity<Object> getAllProjects() {
        return ResponseHandler.response(HttpStatus.OK, projectService.getAllProjects(), true);
    }

    @GetMapping("/type")
    public ResponseEntity<Object> getProjectByType(@RequestParam("projectType") String type) {
        return ResponseHandler.response(HttpStatus.OK, projectService.getProjectByType(type), true);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<Object> getProjectById(@PathVariable("projectId") UUID projectId) {
        return ResponseHandler.response(HttpStatus.OK, projectService.getProjectById(projectId), true);
    }

    @PostMapping
    public ResponseEntity<Object> createProject(@RequestBody AllProjectDto project) {
        return ResponseHandler.response(HttpStatus.OK, projectService.createProject(project), true);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<Object> deleteProject(@PathVariable("projectId") UUID projectId) {
        projectService.deleteProjectById(projectId);
        return ResponseHandler.response(HttpStatus.OK, "Project deleted successfully", true);
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<Object> updateProject(@PathVariable("projectId") UUID projectId, @RequestBody AllProjectDto project) {
        return ResponseHandler.response(HttpStatus.OK, projectService.updateProject(projectId, project), true);
    }

}
