package com.hiper.agq.repository;

import com.hiper.agq.entity.Project;
import com.hiper.agq.entity.enums.TypeProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID> {
    @Query("select p from Project p where p.type = UPPER(?1)")
    List<Project> findByType(String type);

    boolean existsProjectById(UUID id);

}