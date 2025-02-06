package by.koronatech.office.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Employee {
    private Long id;
    private String name;
    private Double salary;
    private String department;
    private Boolean manager;
}
