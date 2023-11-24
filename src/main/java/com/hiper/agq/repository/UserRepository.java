package com.hiper.agq.repository;

import com.hiper.agq.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    @Query("select u from User u inner join u.tasks tasks where tasks.id = ?1")
    List<User> findAllByTasksId(UUID id);

    boolean existsUserById(UUID id);
}