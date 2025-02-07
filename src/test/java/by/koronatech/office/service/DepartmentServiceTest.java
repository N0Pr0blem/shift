package by.koronatech.office.service;

import by.koronatech.office.model.Department;
import by.koronatech.office.repository.DepartmentRepository;
import by.koronatech.office.service.Impl.DepartmentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentServiceImpl departmentService;

    private Department department;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        department = new Department(1L, "IT");
    }

    @Test
    void findAllShouldReturnListOfDepartments() {
        List<Department> departments = List.of(department);
        Pageable pageable = PageRequest.of(0, 5);
        Page<Department> pageOfDepartments = new PageImpl<>(departments,pageable,departments.size());

        when(departmentRepository.findAll(any(Pageable.class))).thenReturn(pageOfDepartments);

        List<Department> result = departmentService.findAll(0, 5);

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("IT");

        verify(departmentRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void findAllShouldReturnEmptyListWhenNoDepartments() {
        when(departmentRepository.findAll(any(Pageable.class))).thenReturn(Page.empty());

        List<Department> result = departmentService.findAll(0, 5);

        assertThat(result).isEmpty();

        verify(departmentRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void findByNameShouldReturnDepartmentWhenExists() {
        when(departmentRepository.findByName("IT")).thenReturn(Optional.of(department));

        Optional<Department> result = departmentService.findByName("IT");

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("IT");

        verify(departmentRepository, times(1)).findByName("IT");
    }

    @Test
    void findByNameShouldReturnEmptyWhenDepartmentNotFound(){
        when(departmentRepository.findByName("HR")).thenReturn(Optional.empty());

        Optional<Department> result = departmentService.findByName("HR");

        assertThat(result).isEmpty();

        verify(departmentRepository, times(1)).findByName("HR");
    }

}