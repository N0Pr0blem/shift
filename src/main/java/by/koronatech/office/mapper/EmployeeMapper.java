package by.koronatech.office.mapper;

import by.koronatech.office.dto.EmployeeDto;
import by.koronatech.office.mapper.base.Mappable;
import by.koronatech.office.model.Employee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper extends Mappable<Employee, EmployeeDto> {
}
