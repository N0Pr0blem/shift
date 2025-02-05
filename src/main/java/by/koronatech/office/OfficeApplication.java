package by.koronatech.office;

import by.koronatech.office.model.Department;
import by.koronatech.office.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.stream.LongStream;

@SpringBootApplication
@RequiredArgsConstructor
public class OfficeApplication implements CommandLineRunner {

    private final DepartmentRepository departmentRepository;

    public static void main(String[] args) {
        SpringApplication.run(OfficeApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        LongStream.range(1, 24)
                .forEach(i -> {
                    departmentRepository.save(new Department().toBuilder()
                            .id(i)
                            .name("department-"+i)
                            .build());
                });
    }

}
