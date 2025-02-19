package by.koronatech.office.service.Impl;

import by.koronatech.office.exception.EmployeeException;
import by.koronatech.office.model.Employee;
import by.koronatech.office.repository.EmployeeRepository;
import by.koronatech.office.service.DepartmentService;
import by.koronatech.office.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public List<Employee> findAllByDepartment(String department) {
        return employeeRepository.findAll()
                .stream()
                .filter(employee -> employee.getDepartment().getName().equals(department))
                .toList();
    }

    @Override
    public Employee makeManager(Long employeeId) {
        Optional<Employee> findEmployee = employeeRepository.findById(employeeId)
                .map(employee -> {
                    if (!employee.getIsManager()) {
                        employee.setIsManager(true);
                        return employeeRepository.save(employee);
                    } else throw new EmployeeException("Employee is already manager");
                });
        if (findEmployee.isPresent()) return findEmployee.get();
        else throw new EmployeeException("No such employee");

    }

    @Override
    public Employee update(Long employeeId, Employee employee) {
        return employeeRepository.findById(employeeId)
                .map(findEmployee -> {
                    if (employee.getName() != null) findEmployee.setName(employee.getName());
                    if (employee.getIsManager() != null) findEmployee.setIsManager(employee.getIsManager());
                    if (employee.getSalary() != null) findEmployee.setSalary(employee.getSalary());
                    if (employee.getDepartment() != null)
                        findEmployee.setDepartment(employee.getDepartment());

                    return employeeRepository.save(findEmployee);
                })
                .orElseThrow(() -> new EmployeeException("Employee not found"));

    }

    @Override
    public void delete(Long employeeId) {
        employeeRepository.deleteById(employeeId);
    }

}
