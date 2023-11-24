package com.hiper.agq.service;

import com.hiper.agq.dao.ProjectDAO;
import com.hiper.agq.dto.AllProjectDto;
import com.hiper.agq.dto.mapper.AllProjectMapper;
import com.hiper.agq.dto.mapper.ProjectMapper;
import com.hiper.agq.entity.Project;
import com.hiper.agq.entity.enums.TypeProject;
import com.hiper.agq.exception.common.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProjectServiceTest {

    @Mock
    private ProjectDAO projectDAO;

    @Mock
    private ProjectMapper projectMapper;

    @Mock
    private AllProjectMapper allProjectMapper;

    @InjectMocks
    private ProjectService projectService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllProjects() {
        // Given
        List<Project> projects = new ArrayList<>();
        when(projectDAO.selectAllProjects()).thenReturn(projects);
        when(allProjectMapper.toDto(any(Project.class))).thenReturn(new AllProjectDto(
                UUID.randomUUID(),
                "Andre",
                "someDescription",
                TypeProject.MOBILE,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(7),
                "https://i.postimg.cc/Hk4cfhrS/profile.jpg"
        ));

        // When
        List<AllProjectDto> result = projectService.getAllProjects();

        // Then
        assertNotNull(result);
        assertEquals(0, result.size());
        verify(projectDAO, times(1)).selectAllProjects();
        verify(allProjectMapper, times(0)).toDto(any(Project.class));
    }

    @Test
    void getProjectByType() {
        // Given
        String type = "someType";
        List<Project> projects = new ArrayList<>();
        when(projectDAO.selectByType(type)).thenReturn(projects);
        when(allProjectMapper.toDto(any(Project.class))).thenReturn(new AllProjectDto(
                UUID.randomUUID(),
                "Andre",
                "someDescription",
                TypeProject.MOBILE,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(7),
                "https://i.postimg.cc/Hk4cfhrS/profile.jpg"
        ));

        // When
        List<AllProjectDto> result = projectService.getProjectByType(type);

        // Then
        assertNotNull(result);
        assertEquals(0, result.size());
        verify(projectDAO, times(1)).selectByType(type);
        verify(allProjectMapper, times(0)).toDto(any(Project.class));
    }

    @Test
    void getProjectById() {
        // Given
        UUID projectId = UUID.randomUUID();
        Project project = new Project();
        when(projectDAO.selectProjectById(projectId)).thenReturn(java.util.Optional.of(project));
        when(allProjectMapper.toDto(project)).thenReturn(new AllProjectDto(
                UUID.randomUUID(),
                "Andre",
                "someDescription",
                TypeProject.MOBILE,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(7),
                "https://i.postimg.cc/Hk4cfhrS/profile.jpg"
        ));

        // When
        AllProjectDto result = projectService.getProjectById(projectId);

        // Then
        assertNotNull(result);
        verify(projectDAO, times(1)).selectProjectById(projectId);
        verify(allProjectMapper, times(1)).toDto(project);
    }

    @Test
    void createProject() {
        // Given
        AllProjectDto projectDto = new AllProjectDto(
                UUID.randomUUID(),
                "Andre",
                "someDescription",
                TypeProject.MOBILE,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(7),
                "https://i.postimg.cc/Hk4cfhrS/profile.jpg"
        );
        Project newProject = new Project();
        when(allProjectMapper.toEntity(projectDto)).thenReturn(newProject);
        when(projectDAO.insertProject(newProject)).thenReturn(newProject);
        when(allProjectMapper.toDto(newProject)).thenReturn(projectDto);

        // When
        AllProjectDto result = projectService.createProject(projectDto);

        // Then
        assertNotNull(result);
        verify(allProjectMapper, times(1)).toEntity(projectDto);
        verify(projectDAO, times(1)).insertProject(newProject);
        verify(allProjectMapper, times(1)).toDto(newProject);
    }

    @Test
    void deleteProjectById() {
        // Given
        UUID projectId = UUID.randomUUID();
        when(projectDAO.existsProjectWithId(projectId)).thenReturn(true);

        // When
        assertDoesNotThrow(() -> projectService.deleteProjectById(projectId));

        // Then
        verify(projectDAO, times(1)).existsProjectWithId(projectId);
        verify(projectDAO, times(1)).deleteProjectById(projectId);
    }

    @Test
    void deleteProjectById_NotFound() {
        // Given
        UUID projectId = UUID.randomUUID();
        when(projectDAO.existsProjectWithId(projectId)).thenReturn(false);

        // When/Then
        assertThrows(ResourceNotFoundException.class, () -> projectService.deleteProjectById(projectId));
        verify(projectDAO, times(1)).existsProjectWithId(projectId);
        verify(projectDAO, times(0)).deleteProjectById(projectId);
    }

    @Test
    void updateProject() {
        // Given
        UUID projectId = UUID.randomUUID();
        AllProjectDto projectDto = new AllProjectDto(
                UUID.randomUUID(),
                "Andre",
                "someDescription",
                TypeProject.MOBILE,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(7),
                "https://i.postimg.cc/Hk4cfhrS/profile.jpg"
        );
        Project project = new Project();
        when(projectDAO.selectProjectById(projectId)).thenReturn(java.util.Optional.of(project));
        when(allProjectMapper.toDto(project)).thenReturn(projectDto);
        when(projectDAO.updateProject(project)).thenReturn(project);

        // When
        AllProjectDto result = projectService.updateProject(projectId, projectDto);

        // Then
        assertNotNull(result);
        verify(projectDAO, times(1)).selectProjectById(projectId);
        verify(allProjectMapper, times(1)).toDto(project);
        verify(projectDAO, times(1)).updateProject(project);
    }

    @Test
    void updateProject_NotFound() {
        // Given
        UUID projectId = UUID.randomUUID();
        AllProjectDto projectDto = new AllProjectDto(
                UUID.randomUUID(),
                "Andre",
                "someDescription",
                TypeProject.MOBILE,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(7),
                "https://i.postimg.cc/Hk4cfhrS/profile.jpg"
        );
        when(projectDAO.selectProjectById(projectId)).thenReturn(java.util.Optional.empty());

        // When/Then
        assertThrows(ResourceNotFoundException.class, () -> projectService.updateProject(projectId, projectDto));
        verify(projectDAO, times(1)).selectProjectById(projectId);
        verify(allProjectMapper, times(0)).toDto(any(Project.class));
        verify(projectDAO, times(0)).updateProject(any(Project.class));
    }
}
