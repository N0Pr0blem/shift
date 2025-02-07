package by.koronatech.office.service;

import by.koronatech.office.model.Employee;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmployeeService {
    Employee save(Employee employee);

    List<Employee> findAll();

    List<Employee> findAllByDepartment(String department);

    Employee makeManager(Long employeeId);

    Employee update(Long employeeId, Employee employee);

    void delete(Long employeeId);
}
