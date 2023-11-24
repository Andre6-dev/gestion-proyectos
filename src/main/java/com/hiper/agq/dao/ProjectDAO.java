package com.hiper.agq.dao;

import com.hiper.agq.entity.Project;
import com.hiper.agq.entity.enums.TypeProject;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * andre on 23/11/2023
 */
public interface ProjectDAO {

    List<Project> selectAllProjects();

    List<Project> selectByType(String type);

    Optional<Project> selectProjectById(UUID id);

    Project insertProject(Project project);

    boolean existsProjectWithId(UUID id);

    void deleteProjectById(UUID id);

    Project updateProject(Project project);

}
