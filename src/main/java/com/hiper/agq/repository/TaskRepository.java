package com.hiper.agq.repository;

import com.hiper.agq.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {

    @Query("select t from Task t where t.project.id = ?1")
    List<Task> findByProject_Id(UUID id);


    @Query("select t from Task t inner join t.users users where users.id = ?1")
    List<Task> findByUsers_Id(UUID id);

    @Query("select t from Task t inner join t.users users where t.project.id = ?1 and users.id = ?2")
    List<Task> findByProject_IdAndUsers_Id(UUID id, UUID id1);


    boolean existsTaskById(UUID id);
}