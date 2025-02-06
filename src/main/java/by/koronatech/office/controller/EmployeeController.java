package by.koronatech.office.controller;

import by.koronatech.office.model.Employee;
import by.koronatech.office.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping()
    public Employee save(@RequestBody Employee employee){
        return employeeService.save(employee);
    }

    @GetMapping()
    public List<Employee> getAll(@RequestParam(required = false) String department){
        if (department != null && !department.isEmpty()) {
            return employeeService.findAllByDepartment(department);
        } else {
            return employeeService.findAll();
        }
    }

    @PatchMapping("/{employeeId}")
    public Employee makeManager(@PathVariable Long employeeId){
        return employeeService.makeManager(employeeId);
    }

    @PostMapping("/{employeeId}")
    public Employee updateEmployee(@PathVariable Long employeeId, @RequestBody Employee employee){
        return employeeService.update(employeeId,employee);
    }

    @DeleteMapping("/{employeeId}")
    public void delete(@PathVariable Long employeeId){
        employeeService.delete(employeeId);
    }
}
