package by.koronatech.office.service.Impl;

import by.koronatech.office.exception.EmployeeException;
import by.koronatech.office.model.Employee;
import by.koronatech.office.repository.EmployeeRepository;
import by.koronatech.office.service.DepartmentService;
import by.koronatech.office.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentService departmentService;

    @Override
    public Employee save(Employee employee) {
        check(employee.getDepartment());
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public List<Employee> findAll(int page, int step) {
        return employeeRepository.findAll(page,step);
    }

    @Override
    public List<Employee> findAllByDepartment(String department) {
        return employeeRepository.findAll()
                .stream()
                .filter(employee -> employee.getDepartment().equals(department))
                .toList();
    }

    @Override
    public Employee makeManager(Long employeeId) {
        return employeeRepository.findById(employeeId)
                .toBuilder()
                .manager(true)
                .build();
    }

    @Override
    public Employee update(Long employeeId, Employee employee) {
        Employee findEmployee = employeeRepository.findById(employeeId);
        if (employee.getName() != null) findEmployee.setName(employee.getName());
        if (employee.getManager() != null) findEmployee.setManager(employee.getManager());
        if (employee.getSalary() != null) findEmployee.setSalary(employee.getSalary());
        if (employee.getDepartment() != null && check(employee.getDepartment()))
            findEmployee.setDepartment(employee.getDepartment());

        return employeeRepository.save(findEmployee);
    }

    @Override
    public void delete(Long employeeId) {
        employeeRepository.delete(employeeId);
    }

    private boolean check(String department) {
        if (departmentService.check(department))
            return true;
        else throw new EmployeeException("No such department for employee");
    }
}
