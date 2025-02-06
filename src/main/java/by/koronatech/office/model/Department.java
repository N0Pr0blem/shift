package by.koronatech.office.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Department {
    private Long id;
    private String name;
}
