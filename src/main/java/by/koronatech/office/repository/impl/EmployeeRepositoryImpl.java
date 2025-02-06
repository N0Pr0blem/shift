package by.koronatech.office.repository.impl;

import by.koronatech.office.exception.EmployeeException;
import by.koronatech.office.model.Employee;
import by.koronatech.office.repository.EmployeeRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {

    private List<Employee> employees = new ArrayList<>();

    @Override
    public Employee save(Employee employee) {
        for(Employee listEmployee:employees){
            if(listEmployee.getId().equals(employee.getId())){
                listEmployee.setName(employee.getName());
                listEmployee.setDepartment(employee.getDepartment());
                listEmployee.setSalary(employee.getSalary());
                listEmployee.setManager(employee.getManager());
                return listEmployee;
            }
        }
        employees.add(employee);
        return employee;
    }

    @Override
    public Employee findById(Long id) {
        Optional<Employee> employee = employees.stream()
                .filter(findEmployee -> findEmployee.getId().equals(id))
                .findFirst();
        if(employee.isPresent()) return employee.get();
        else throw new EmployeeException("Employee not found");
    }

    @Override
    public List<Employee> findAll() {
        return employees;
    }

    @Override
    public List<Employee> findAll(int page, int step) {
        int start = (page - 1) * step;
        int end = Math.min(start+step, employees.size());

        if (start > employees.size() || start < 0) {
            return new ArrayList<>();
        }

        return employees.subList(start,end);
    }

    @Override
    public void delete(Long id) {
        employees.remove(findById(id));
    }
}
