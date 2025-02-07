package by.koronatech.office.mapper;

import by.koronatech.office.dto.EmployeeDto;
import by.koronatech.office.mapper.base.Mappable;
import by.koronatech.office.model.Department;
import by.koronatech.office.model.Employee;
import by.koronatech.office.service.DepartmentService;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class EmployeeMapper implements Mappable<Employee, EmployeeDto> {

    @Autowired
    protected DepartmentService departmentService;

    @Override
    @Mapping(target = "department", source = "departmentName", qualifiedByName = "mapFromDepartmentDto")
    public abstract Employee toEntity(EmployeeDto dto);

    @Override
    @Mapping(target = "departmentName", source = "department", qualifiedByName = "mapToDepartmentDto")
    public abstract EmployeeDto toDto(Employee entity);

    @Named("mapFromDepartmentDto")
    protected Department mapFromDepartmentDto(String departmentName) {
        if (departmentName == null) {
            return null;
        }
        return departmentService.findByName(departmentName)
                .orElseThrow(() -> new RuntimeException("Department not found: " + departmentName));
    }

    @Named("mapToDepartmentDto")
    protected String mapToDepartmentDto(Department department) {
        return (department == null) ? null : department.getName();
    }
}
