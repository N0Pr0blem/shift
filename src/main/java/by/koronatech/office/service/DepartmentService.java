package by.koronatech.office.service;

import by.koronatech.office.model.Department;

import java.util.List;

public interface DepartmentService {
    List<Department> findAll();

    List<Department> findAll(int page, int step);

    boolean check(String department);

}
