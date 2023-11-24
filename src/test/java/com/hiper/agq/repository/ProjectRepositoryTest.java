package com.hiper.agq.repository;

import com.hiper.agq.entity.Project;
import com.hiper.agq.entity.enums.TypeProject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testFindByType() {
        // Create test projects
        Project project1 = new Project();

        String dateStart = "2023-11-25T14:30:00";
        String dateEnd = "2024-11-25T14:30:00";

        project1.setName("Project1");
        project1.setDescription("Description1");
        project1.setType(TypeProject.WEB);
        project1.setStartDate(LocalDateTime.parse(dateStart));
        project1.setEndDate(LocalDateTime.parse(dateEnd));

        Project project2 = new Project();

        project2.setName("Project2");
        project2.setDescription("Description2");
        project2.setType(TypeProject.MOBILE);
        project2.setStartDate(LocalDateTime.parse(dateStart));
        project2.setEndDate(LocalDateTime.parse(dateEnd));

        // Call the method under test
        List<Project> projects = projectRepository.findByType("WEB");

        // Assert the result
        assertEquals(3, projects.size());
        assertEquals(TypeProject.WEB, projects.get(0).getType());
    }

    @Test
    public void testExistsProjectById() {
        // Create a test project
        Project project = new Project();
        String dateStart = "2023-11-25T14:30:00";
        String dateEnd = "2024-11-25T14:30:00";

        project.setName("Project1");
        project.setDescription("Description1");
        project.setType(TypeProject.WEB);
        project.setStartDate(LocalDateTime.parse(dateStart));
        project.setEndDate(LocalDateTime.parse(dateEnd));
        entityManager.persist(project);

        // Call the method under test
        boolean exists = projectRepository.existsProjectById(project.getId());

        // Assert the result
        assertTrue(exists);
    }
}