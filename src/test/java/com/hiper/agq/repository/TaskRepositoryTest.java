package com.hiper.agq.repository;

import com.hiper.agq.entity.Project;
import com.hiper.agq.entity.Task;
import com.hiper.agq.entity.User;
import com.hiper.agq.entity.enums.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void findByProject_Id() {
        // Given
        Project project = new Project();
        project.setName("Project1");
        project.setDescription("Description1");
        project.setType(TypeProject.WEB);
        project.setStartDate(LocalDateTime.now());
        project.setEndDate(LocalDateTime.now().plusDays(7));

        entityManager.persist(project);

        Task task1 = new Task();
        task1.setName("Task1");
        task1.setDescription("Description1");
        task1.setStatus(TaskStatus.IN_PROGRESS);
        task1.setPriority(PriorityEnum.HIGH);
        task1.setType(TypeTask.MOBILE);
        task1.setStartDate(LocalDateTime.now());
        task1.setEndDate(LocalDateTime.now().plusDays(2));
        task1.setEstimatedTime("2h");
        task1.setProject(project);

        Task task2 = new Task();
        task2.setName("Task2");
        task2.setDescription("Description2");
        task2.setStatus(TaskStatus.IN_PROGRESS);
        task2.setPriority(PriorityEnum.LOW);
        task2.setType(TypeTask.OTHER);
        task2.setStartDate(LocalDateTime.now());
        task2.setEndDate(LocalDateTime.now().plusDays(3));
        task2.setEstimatedTime("2h");
        task2.setProject(project);

        entityManager.persist(task1);
        entityManager.persist(task2);

        // When
        List<Task> actual = taskRepository.findByProject_Id(project.getId());

        // Then
        assertEquals(2, actual.size());
    }

    @Test
    void findByUsers_Id() {
        // Given
        Project project = new Project();
        project.setName("Project1");
        project.setDescription("Description1");
        project.setType(TypeProject.WEB);
        project.setStartDate(LocalDateTime.now());
        project.setEndDate(LocalDateTime.now().plusDays(7));

        entityManager.persist(project);

        User user = new User();
        user.setFullName("User1");
        user.setEmail("user1@example.com");
        user.setTypeUser(TypeUser.DEVELOPER);
        user.setMobilePhone("123456789");
        user.setProfilePicture("https://example.com/user1.jpg");

        entityManager.persist(user);

        Task task1 = new Task();
        task1.setName("Task1");
        task1.setDescription("Description1");
        task1.setStatus(TaskStatus.IN_PROGRESS);
        task1.setPriority(PriorityEnum.HIGH);
        task1.setType(TypeTask.MOBILE);
        task1.setStartDate(LocalDateTime.now());
        task1.setEndDate(LocalDateTime.now().plusDays(2));
        task1.setEstimatedTime("2h");
        task1.setProject(project);
        task1.getUsers().add(user);

        Task task2 = new Task();
        task2.setName("Task2");
        task2.setDescription("Description2");
        task2.setStatus(TaskStatus.IN_PROGRESS);
        task2.setPriority(PriorityEnum.LOW);
        task2.setType(TypeTask.BACKEND);
        task2.setStartDate(LocalDateTime.now());
        task2.setEndDate(LocalDateTime.now().plusDays(3));
        task2.setEstimatedTime("2h");
        task2.setProject(project);
        task2.getUsers().add(user);

        entityManager.persist(task1);
        entityManager.persist(task2);

        // When
        List<Task> actual = taskRepository.findByUsers_Id(user.getId());

        // Then
        assertEquals(2, actual.size());
    }

    @Test
    void findByProject_IdAndUsers_Id() {
        // Given
        Project project = new Project();
        project.setName("Project1");
        project.setDescription("Description1");
        project.setType(TypeProject.WEB);
        project.setStartDate(LocalDateTime.now());
        project.setEndDate(LocalDateTime.now().plusDays(7));

        entityManager.persist(project);

        User user = new User();
        user.setFullName("User1");
        user.setEmail("user1@example.com");
        user.setTypeUser(TypeUser.DEVELOPER);
        user.setMobilePhone("123456789");
        user.setProfilePicture("https://example.com/user1.jpg");

        entityManager.persist(user);

        Task task1 = new Task();
        task1.setName("Task1");
        task1.setDescription("Description1");
        task1.setStatus(TaskStatus.IN_PROGRESS);
        task1.setPriority(PriorityEnum.HIGH);
        task1.setType(TypeTask.MOBILE);
        task1.setStartDate(LocalDateTime.now());
        task1.setEndDate(LocalDateTime.now().plusDays(2));
        task1.setEstimatedTime("2h");
        task1.setProject(project);
        task1.getUsers().add(user);

        Task task2 = new Task();
        task2.setName("Task2");
        task2.setDescription("Description2");
        task2.setStatus(TaskStatus.IN_PROGRESS);
        task2.setPriority(PriorityEnum.LOW);
        task2.setType(TypeTask.BACKEND);
        task2.setStartDate(LocalDateTime.now());
        task2.setEndDate(LocalDateTime.now().plusDays(3));
        task2.setEstimatedTime("2h");
        task2.setProject(project);
        task2.getUsers().add(user);

        entityManager.persist(task1);
        entityManager.persist(task2);

        // When
        List<Task> actual = taskRepository.findByProject_IdAndUsers_Id(project.getId(), user.getId());

        // Then
        assertEquals(2, actual.size());
    }

    @Test
    void existsTaskById() {

        Project project = new Project();
        project.setName("Project1");
        project.setDescription("Description1");
        project.setType(TypeProject.WEB);
        project.setStartDate(LocalDateTime.now());
        project.setEndDate(LocalDateTime.now().plusDays(7));

        entityManager.persist(project);

        User user = new User();
        user.setFullName("User1");
        user.setEmail("user1@example.com");
        user.setTypeUser(TypeUser.DEVELOPER);
        user.setMobilePhone("123456789");
        user.setProfilePicture("https://example.com/user1.jpg");

        entityManager.persist(user);

        // Given
        Task task = new Task();
        task.setName("Task1");
        task.setDescription("Description1");
        task.setStatus(TaskStatus.IN_PROGRESS);
        task.setPriority(PriorityEnum.HIGH);
        task.setType(TypeTask.MOBILE);
        task.setStartDate(LocalDateTime.now());
        task.setEndDate(LocalDateTime.now().plusDays(2));
        task.setEstimatedTime("2h");

        task.setProject(project);
        task.getUsers().add(user);

        taskRepository.save(task);

        // When
        boolean actual = taskRepository.existsTaskById(task.getId());

        // Then
        assertTrue(actual);
    }
}