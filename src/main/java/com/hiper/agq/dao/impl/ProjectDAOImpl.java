package com.hiper.agq.dao.impl;

import com.hiper.agq.dao.ProjectDAO;
import com.hiper.agq.entity.Project;
import com.hiper.agq.entity.enums.TypeProject;
import com.hiper.agq.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * andre on 23/11/2023
 */
@Repository("projectDAO")
@RequiredArgsConstructor
public class ProjectDAOImpl implements ProjectDAO {

    private final ProjectRepository projectRepository;

    @Override
    public List<Project> selectAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public List<Project> selectByType(String type) {
        return projectRepository.findByType(type);
    }

    @Override
    public Optional<Project> selectProjectById(UUID id) {
        return projectRepository.findById(id);
    }


    @Override
    public Project insertProject(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public boolean existsProjectWithId(UUID id) {
        return projectRepository.existsProjectById(id);
    }

    @Override
    public void deleteProjectById(UUID id) {
        projectRepository.deleteById(id);
    }

    @Override
    public Project updateProject(Project project) {
        return projectRepository.save(project);
    }
}
