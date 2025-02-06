package by.koronatech.office.controller;

import by.koronatech.office.model.Department;
import by.koronatech.office.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping()
    public List<Department> getAll(@RequestParam(defaultValue = "1") int page,
                                   @RequestParam(defaultValue = "10") int step) {
        return departmentService.findAll(page,step);
    }
}
