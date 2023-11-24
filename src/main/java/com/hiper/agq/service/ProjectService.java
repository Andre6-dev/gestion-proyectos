package com.hiper.agq.service;

import com.hiper.agq.dao.ProjectDAO;
import com.hiper.agq.dto.ProjectDto;
import com.hiper.agq.dto.mapper.ProjectMapper;
import com.hiper.agq.entity.Project;
import com.hiper.agq.entity.enums.TypeProject;
import com.hiper.agq.exception.common.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * andre on 23/11/2023
 */
@Slf4j(topic = "ProjectService")
@Service
public class ProjectService {

    private final ProjectDAO projectDAO;

    private final ProjectMapper projectMapper;

    public ProjectService(@Qualifier("projectDAO") ProjectDAO projectDAO, ProjectMapper projectMapper) {
        this.projectDAO = projectDAO;
        this.projectMapper = projectMapper;
    }

    public List<ProjectDto> getAllProjects() {
        log.info("Getting all projects");
        return projectDAO.selectAllProjects()
                .stream()
                .map(projectMapper::toDto)
                .toList();
    }

    public List<ProjectDto> getProjectByType(String type) {
        log.info("Getting project with type [{}]", type);
        return this.projectDAO.selectByType(type)
                .stream()
                .map(projectMapper::toDto)
                .toList();
    }

    public ProjectDto getProjectById(UUID projectId) {
        log.info("Getting project with id [{}]", projectId);
        return this.projectDAO.selectProjectById(projectId)
                .map(projectMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Project with id [%s] not found".formatted(projectId)));
    }

    public ProjectDto createProject(ProjectDto projectDto) {
        log.info("Creating project with name [{}]", projectDto.name());

        Project newProject = projectDAO.insertProject(projectMapper.toEntity(projectDto));

        return projectMapper.toDto(newProject);
    }

    public void deleteProjectById(UUID projectId) {
        log.info("Deleting project with id [{}]", projectId);

        if (!projectDAO.existsProjectWithId(projectId)) {
            throw new ResourceNotFoundException("Project with id [%s] not found".formatted(projectId));
        }
        projectDAO.deleteProjectById(projectId);
    }

    public ProjectDto updateProject(UUID projectId, ProjectDto projectDto) {
        log.info("Updating project with id [{}]", projectId);

        Project project = projectDAO.selectProjectById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project with id [%s] not found".formatted(projectId)));

        boolean changes = false;

        if (projectDto.name() != null && !projectDto.name().equals(project.getName())) {
            project.setName(projectDto.name());
            changes = true;
        }

        if (projectDto.description() != null && !projectDto.description().equals(project.getDescription())) {
            project.setDescription(projectDto.description());
            changes = true;
        }

        if (projectDto.type() != null && !projectDto.type().equals(project.getType())) {
            project.setType(TypeProject.valueOf(projectDto.type()));
            changes = true;
        }

        if (!changes) {
            throw new RuntimeException("No changes were made");
        }

        Project updatedProject = projectDAO.updateProject(projectMapper.toEntity(projectDto));

        return projectMapper.toDto(updatedProject);
    }
}
