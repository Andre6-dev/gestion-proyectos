package com.hiper.agq.service;

import com.hiper.agq.dao.ProjectDAO;
import com.hiper.agq.dto.AllProjectDto;
import com.hiper.agq.dto.ProjectDto;
import com.hiper.agq.dto.mapper.AllProjectMapper;
import com.hiper.agq.dto.mapper.ProjectMapper;
import com.hiper.agq.entity.Project;
import com.hiper.agq.entity.enums.TypeProject;
import com.hiper.agq.exception.common.RequestValidationException;
import com.hiper.agq.exception.common.ResourceNotFoundException;
import com.hiper.agq.utils.UpdateHelper;
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
@Slf4j(topic = "ProjectService")
@Service
public class ProjectService {

    private final ProjectDAO projectDAO;

    private final ProjectMapper projectMapper;

    private final AllProjectMapper allProjectMapper;

    public ProjectService(@Qualifier("projectDAO") ProjectDAO projectDAO, ProjectMapper projectMapper, AllProjectMapper allProjectMapper) {
        this.projectDAO = projectDAO;
        this.projectMapper = projectMapper;
        this.allProjectMapper = allProjectMapper;
    }

    public List<AllProjectDto> getAllProjects() {
        log.info("Getting all projects");
        return this.projectDAO.selectAllProjects()
                .stream()
                .map(allProjectMapper::toDto)
                .toList();
    }

    public List<AllProjectDto> getProjectByType(String type) {
        log.info("Getting project with type [{}]", type);
        return this.projectDAO.selectByType(type)
                .stream()
                .map(allProjectMapper::toDto)
                .toList();
    }

    public AllProjectDto getProjectById(UUID projectId) {
        log.info("Getting project with id [{}]", projectId);
        return this.projectDAO.selectProjectById(projectId)
                .map(allProjectMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Project with id [%s] not found".formatted(projectId)));
    }

    public AllProjectDto createProject(AllProjectDto projectDto) {
        log.info("Creating project with name [{}]", projectDto.name());

        Project newProject = projectDAO.insertProject(allProjectMapper.toEntity(projectDto));

        return allProjectMapper.toDto(newProject);
    }

    public void deleteProjectById(UUID projectId) {
        log.info("Deleting project with id [{}]", projectId);

        if (!projectDAO.existsProjectWithId(projectId)) {
            throw new ResourceNotFoundException("Project with id [%s] not found".formatted(projectId));
        }
        projectDAO.deleteProjectById(projectId);
    }

    public AllProjectDto updateProject(UUID projectId, AllProjectDto projectDto) {
        log.info("Updating project with id [{}]", projectId);

        Project project = projectDAO.selectProjectById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project with id [%s] not found".formatted(projectId)));

        boolean changes = UpdateHelper.updateField(projectDto.name(), project.getName(), project::setName) ||
                UpdateHelper.updateField(projectDto.description(), project.getDescription(), project::setDescription) ||
                UpdateHelper.updateEnumField(Objects.requireNonNull(projectDto.type()).toString(), project.getType(), TypeProject::valueOf, project::setType) ||
                UpdateHelper.updateField(projectDto.startDate(), project.getStartDate(), project::setStartDate) ||
                UpdateHelper.updateField(projectDto.endDate(), project.getEndDate(), project::setEndDate);

        if (!changes) {
            throw new RequestValidationException("No changes detected");
        }

        return allProjectMapper.toDto(projectDAO.updateProject(project));
    }


}
