package by.koronatech.office.controller;

import by.koronatech.office.dto.EmployeeDto;
import by.koronatech.office.mapper.EmployeeMapper;
import by.koronatech.office.model.Department;
import by.koronatech.office.model.Employee;
import by.koronatech.office.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class EmployeeControllerTest {
    @Mock
    private EmployeeMapper employeeMapper;

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    MockMvc mockMvc;
    Department department;
    Employee employee;
    EmployeeDto employeeDto;
    List<Employee> employees;
    List<EmployeeDto> employeeDtos;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();

        department = Department.builder().id(1L).name("IT").build();
        employee = new Employee(1L, "empty", 12D, department, false);
        employeeDto = new EmployeeDto(1L, "empty", 12D, "IT", false);
        employees = List.of(employee);
        employeeDtos = List.of(employeeDto);

        when(employeeMapper.toDto(employee)).thenReturn(employeeDto);
        when(employeeMapper.toDtos(any())).thenReturn(employeeDtos);

        when(employeeMapper.toEntity(employeeDto)).thenReturn(employee);
        when(employeeMapper.toEntities(any())).thenReturn(employees);
    }

    @Test
    void saveEmployeeExpectedEmployeeDto() throws Exception {
        String employeeDtoJson = new ObjectMapper().writeValueAsString(employeeDto);

        when(employeeService.save(employee)).thenReturn(employee);

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeDtoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("empty"))
                .andExpect(jsonPath("$.salary").value(12D))
                .andExpect(jsonPath("$.department_name").value("IT"))
                .andExpect(jsonPath("$.is_manager").value(false));

        verify(employeeService, times(1)).save(employee);
        verify(employeeMapper, times(1)).toDto(employee);
        verify(employeeMapper, times(1)).toEntity(employeeDto);
    }

    @Test
    void findAllByDepartmentEmployeesExpectedListOfEmployees() throws Exception {
        when(employeeService.findAllByDepartment(department.getName())).thenReturn(employees);

        mockMvc.perform(get("/api/employees")
                        .param("department", department.getName()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]").exists());

        verify(employeeService,times(1)).findAllByDepartment(department.getName());
        verify(employeeMapper, times(1)).toDtos(any());
    }

    @Test
    void makeManagerTestExpectedUpdateField() throws Exception {
        employeeDto.setIsManager(true);
        when(employeeService.makeManager(1L)).thenReturn(employee);

        mockMvc.perform(patch("/api/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.is_manager").value(true));

        verify(employeeService, times(1)).makeManager(1L);
        verify(employeeMapper, times(1)).toDto(employee);
    }

    @Test
    void updateEmployeeExpectedUpdatedEmployee() throws Exception {
        Employee updatedEmployee = new Employee(1L, "Updated", 15D, department, true);
        EmployeeDto updatedEmployeeDto = new EmployeeDto(1L, "Updated", 15D, "IT", true);

        when(employeeService.update(eq(1L), any())).thenReturn(updatedEmployee);
        when(employeeMapper.toDto(updatedEmployee)).thenReturn(updatedEmployeeDto);

        mockMvc.perform(post("/api/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedEmployeeDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Updated"))
                .andExpect(jsonPath("$.salary").value(15D))
                .andExpect(jsonPath("$.department_name").value("IT"))
                .andExpect(jsonPath("$.is_manager").value(true));

        verify(employeeService, times(1)).update(eq(1L), any());
        verify(employeeMapper, times(1)).toDto(updatedEmployee);
    }

    @Test
    void deleteEmployeeExpectedNoContent() throws Exception {
        doNothing().when(employeeService).delete(1L);

        mockMvc.perform(delete("/api/employees/1"))
                .andExpect(status().isOk());

        verify(employeeService, times(1)).delete(1L);
    }
}