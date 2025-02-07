package by.koronatech.office.service.Impl;

import by.koronatech.office.model.Department;
import by.koronatech.office.repository.DepartmentRepository;
import by.koronatech.office.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    @Override
    public List<Department> findAll(int page, int step) {
        return departmentRepository.findAll(PageRequest.of(page,step)).stream().toList();
    }

    @Override
    public Optional<Department> findByName(String departmentName) {
        return departmentRepository.findByName(departmentName);
    }
}
