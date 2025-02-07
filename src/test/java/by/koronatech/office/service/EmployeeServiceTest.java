package by.koronatech.office.service;

import by.koronatech.office.exception.EmployeeException;
import by.koronatech.office.model.Department;
import by.koronatech.office.model.Employee;
import by.koronatech.office.repository.EmployeeRepository;
import by.koronatech.office.service.Impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;
    private Department department;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        department = new Department(1L, "IT");
        employee = new Employee(1L, "John Doe", 5000.0, department, false);
    }

    @Test
    void saveShouldReturnSavedEmployee() {
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        Employee result = employeeService.save(employee);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("John Doe");

        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void findAllShouldReturnListOfEmployees() {
        when(employeeRepository.findAll()).thenReturn(List.of(employee));

        List<Employee> result = employeeService.findAll();

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("John Doe");

        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void findAllByDepartmentShouldReturnEmployeesFromDepartment() {
        when(employeeRepository.findAll()).thenReturn(List.of(employee));

        List<Employee> result = employeeService.findAllByDepartment("IT");

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getDepartment().getName()).isEqualTo("IT");

        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void makeManagerShouldUpdateEmployeeToManager() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        Employee result = employeeService.makeManager(1L);

        assertThat(result.getIsManager()).isTrue();

        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void makeManagerShouldThrowException() {
        employee.setIsManager(true);

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        assertThatThrownBy(() -> employeeService.makeManager(1L));

        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    void makeManagerShouldThrowExceptionWhenEmployeeNotFound() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> employeeService.makeManager(1L))
                .isInstanceOf(EmployeeException.class);

        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void updateShouldReturnUpdatedEmployee() {
        Employee updatedEmployee = new Employee(1L, "Jane Doe", 6000.0, department, true);

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(updatedEmployee);

        Employee result = employeeService.update(1L, updatedEmployee);

        assertThat(result.getName()).isEqualTo("Jane Doe");
        assertThat(result.getSalary()).isEqualTo(6000.0);
        assertThat(result.getIsManager()).isTrue();

        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void updateShouldThrowExceptionWhenEmployeeNotFound() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        Employee updatedEmployee = new Employee(1L, "Jane Doe", 6000.0, department, true);

        assertThatThrownBy(() -> employeeService.update(1L, updatedEmployee))
                .isInstanceOf(EmployeeException.class)
                .hasMessage("Employee not found");

        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void deleteShouldRemoveEmployee() {
        doNothing().when(employeeRepository).deleteById(1L);

        employeeService.delete(1L);

        verify(employeeRepository, times(1)).deleteById(1L);
    }

}
