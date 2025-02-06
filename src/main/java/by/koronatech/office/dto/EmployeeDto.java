package by.koronatech.office.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class EmployeeDto {
    private Long id;
    private String name;
    private Double salary;
    private String departmentName;
    private Boolean isManager;
}
