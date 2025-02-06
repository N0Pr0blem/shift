package by.koronatech.office.service;


import by.koronatech.office.model.Department;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DepartmentService {
    List<Department> findAll();

    Page<Department> findAll(int page, int step);

    boolean check(String department);

}
