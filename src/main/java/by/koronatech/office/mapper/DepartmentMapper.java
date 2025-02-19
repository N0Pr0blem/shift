package by.koronatech.office.mapper;

import by.koronatech.office.dto.DepartmentDto;
import by.koronatech.office.mapper.base.Mappable;
import by.koronatech.office.model.Department;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface DepartmentMapper extends Mappable<Department, DepartmentDto> {
}
