package by.koronatech.office.service;


import by.koronatech.office.model.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {

    List<Department> findAll(int page, int step);

    Optional<Department> findByName(String departmentName);

}
