package by.koronatech.office.controller;

import by.koronatech.office.dto.EmployeeDto;
import by.koronatech.office.mapper.EmployeeMapper;
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
    private final EmployeeMapper employeeMapper;

    @PostMapping()
    public EmployeeDto save(@RequestBody EmployeeDto employeeDto){
        return employeeMapper.toDto(employeeService.save(employeeMapper.toEntity(employeeDto)));
    }

    @GetMapping()
    public List<EmployeeDto> getAll(@RequestParam(required = false) String department){
        if (department != null && !department.isEmpty()) {
            return employeeMapper.toDtos(employeeService.findAllByDepartment(department));
        } else {
            return employeeMapper.toDtos(employeeService.findAll());
        }
    }

    @PatchMapping("/{employeeId}")
    public EmployeeDto makeManager(@PathVariable Long employeeId){
        return employeeMapper.toDto(employeeService.makeManager(employeeId));
    }

    @PostMapping("/{employeeId}")
    public EmployeeDto updateEmployee(@PathVariable Long employeeId, @RequestBody EmployeeDto employeeDto){
        return employeeMapper.toDto(employeeService.update(employeeId,employeeMapper.toEntity(employeeDto)));
    }

    @DeleteMapping("/{employeeId}")
    public void delete(@PathVariable Long employeeId){
        employeeService.delete(employeeId);
    }
}
