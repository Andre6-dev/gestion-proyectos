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
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void findAllByTasksId() {

        String dateStart = "2023-11-25T14:30:00";
        String dateEnd = "2024-11-25T14:30:00";

        Project project1 = new Project();

        project1.setName("Project1");
        project1.setDescription("Description1");
        project1.setType(TypeProject.WEB);
        project1.setStartDate(LocalDateTime.parse(dateStart));
        project1.setEndDate(LocalDateTime.parse(dateEnd));

        entityManager.persist(project1);

        // Given
        User user = new User();
        user.setFullName("User1");
        user.setEmail("andre@gmail.com");
        user.setTypeUser(TypeUser.DEVELOPER);
        user.setMobilePhone("123456789");
        user.setProfilePicture("https://i.postimg.cc/Hk4cfhrS/profile.jpg");

        entityManager.persist(user);

        User user2 = new User();
        user2.setFullName("User2");
        user2.setEmail("andre2@gmail.com");
        user2.setTypeUser(TypeUser.DEVELOPER);
        user2.setMobilePhone("123456789");
        user2.setProfilePicture("https://i.postimg.cc/Hk4cfhrS/profile.jpg");

        entityManager.persist(user2);

        Task task = new Task();
        task.setName("Task1");
        task.setDescription("Description1");
        task.setStatus(TaskStatus.IN_PROGRESS);
        task.setPriority(PriorityEnum.HIGH);
        task.setType(TypeTask.MOBILE);
        task.setStartDate(LocalDateTime.parse(dateStart));
        task.setEndDate(LocalDateTime.parse(dateEnd));
        task.setEstimatedTime("2h");

        Task task2 = new Task();
        task2.setName("Task1");
        task2.setDescription("Description1");
        task2.setStatus(TaskStatus.IN_PROGRESS);
        task2.setPriority(PriorityEnum.HIGH);
        task2.setType(TypeTask.MOBILE);
        task2.setStartDate(LocalDateTime.parse(dateStart));
        task2.setEndDate(LocalDateTime.parse(dateEnd));
        task2.setEstimatedTime("2h");

        task.setProject(project1);

        task2.setProject(project1);

        task.getUsers().add(user);
        task.getUsers().add(user2);

        task2.getUsers().add(user);
        task2.getUsers().add(user2);

        entityManager.persist(task);
        entityManager.persist(task2);

        // When
        List<User> actual = userRepository.findAllByTasksId(task.getId());

        // Then
        assertEquals(2, actual.size());
    }

    @Test
    void existsUserById() {
        // Given
        User user = new User();
        user.setFullName("Andre");
        user.setEmail("andre2@gmail.com");
        user.setTypeUser(TypeUser.DEVELOPER);
        user.setMobilePhone("123456789");
        user.setProfilePicture("https://i.postimg.cc/Hk4cfhrS/profile.jpg");

        userRepository.save(user);

        // When
        boolean actual = userRepository.existsUserById(user.getId());

        // Then
        assertTrue(actual);
    }
}