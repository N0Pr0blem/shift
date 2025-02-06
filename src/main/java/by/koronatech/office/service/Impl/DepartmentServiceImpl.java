package by.koronatech.office.service.Impl;

import by.koronatech.office.model.Department;
import by.koronatech.office.repository.DepartmentRepository;
import by.koronatech.office.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    @Override
    public Page<Department> findAll(int page, int step) {
        return departmentRepository.findAll(PageRequest.of(page,step));
    }

    @Override
    public boolean check(String department) {
        return departmentRepository.findByName(department).isPresent();
    }
}
