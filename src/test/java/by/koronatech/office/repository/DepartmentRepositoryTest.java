package by.koronatech.office.repository;

import by.koronatech.office.model.Department;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@ExtendWith(SpringExtension.class)
@DataJpaTest
class DepartmentRepositoryTest {

    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres:15"))
            .withDatabaseName("test_db")
            .withUsername("root")
            .withPassword("root");

    @BeforeAll
    static void startContainer() {
        postgreSQLContainer.start();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    void saveShouldPersistDepartment() {
        Department savedDepartment = departmentRepository.save(new Department().toBuilder().name("IT").build());

        assertThat(savedDepartment).isNotNull();
        assertThat(savedDepartment.getId()).isNotNull();
        assertThat(savedDepartment.getName()).isEqualTo("IT");
    }

    @Test
    void findByNameShouldReturnDepartment() {
        departmentRepository.save(new Department().toBuilder().name("HR").build());

        Optional<Department> found = departmentRepository.findByName("HR");

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("HR");
    }

    @Test
    void findByNameShouldReturnEmptyIfNotFound() {
        Optional<Department> found = departmentRepository.findByName("Finance");

        assertThat(found).isEmpty();
    }
}
