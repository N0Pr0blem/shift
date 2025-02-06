package by.koronatech.office.repository;

import by.koronatech.office.model.Employee;

import java.util.List;

public interface EmployeeRepository {
    Employee save(Employee employee);

    Employee findById(Long id);

    List<Employee> findAll();

    List<Employee> findAll(int page, int step);

    void delete(Long id);
}
