package by.koronatech.office.controller;


import by.koronatech.office.dto.DepartmentDto;
import by.koronatech.office.mapper.DepartmentMapper;
import by.koronatech.office.model.Department;
import by.koronatech.office.service.DepartmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class DepartmentControllerTest {
    @Mock
    private DepartmentService departmentService;

    @Mock
    private DepartmentMapper departmentMapper;

    @InjectMocks
    private DepartmentController departmentController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(departmentController).build();
    }

    @Test
    void getAllDepartmentsShouldReturnListOfDepartments() throws Exception {
        Department department = new Department(1L, "IT");
        DepartmentDto departmentDto = new DepartmentDto(1L, "IT");
        when(departmentService.findAll(0, 5)).thenReturn(List.of(department));
        when(departmentMapper.toDtos(any())).thenReturn(Collections.singletonList(departmentDto));

        mockMvc.perform(get("/api/departments")
                        .param("page", "0")
                        .param("step", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]").exists());

        verify(departmentService, times(1)).findAll(0, 5);
        verify(departmentMapper, times(1)).toDtos(any());
    }

    @Test
    void getAllDepartmentsShouldReturnEmptyList() throws Exception {
        when(departmentService.findAll(0, 5)).thenReturn(Collections.emptyList());
        when(departmentMapper.toDtos(any())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/departments")
                        .param("page", "0")
                        .param("step", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());

        verify(departmentService, times(1)).findAll(0, 5);
        verify(departmentMapper, times(1)).toDtos(any());
    }

}