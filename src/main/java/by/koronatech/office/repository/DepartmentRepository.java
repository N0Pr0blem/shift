package by.koronatech.office.repository;

import by.koronatech.office.model.Department;

import java.util.List;
import java.util.Optional;


public interface DepartmentRepository {
    void save(Department department);

    List<Department> getAll();
    List<Department> getAll(int page, int step);

    Optional<Department> findByName(String department);
}
