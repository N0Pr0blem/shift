package by.koronatech.office.repository.impl;

import by.koronatech.office.model.Department;
import by.koronatech.office.repository.DepartmentRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class DepartmentRepositoryImpl implements DepartmentRepository {
    private List<Department> departments = new ArrayList<>();

    @Override
    public void save(Department department) {
        departments.add(department);
    }

    @Override
    public List<Department> getAll() {
        return departments;
    }

    @Override
    public List<Department> getAll(int page, int step) {
        int start = (page - 1) * step;
        int end = Math.min(start+step, departments.size());

        if (start > departments.size() || start < 0) {
            return new ArrayList<>();
        }

        return departments.subList(start,end);
    }

    @Override
    public Optional<Department> findByName(String departmentName) {
        return departments.stream()
                .filter(department -> department.getName().equals(departmentName))
                .findFirst();
    }

}
