package kriach.bot.services;

import kriach.bot.entities.DegreeName;
import kriach.bot.entities.Department;
import kriach.bot.entities.Lector;
import kriach.bot.repositories.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public Optional<Department> getDepartmentByNameIgnoreCase(String departmentName) {
        return departmentRepository.findByNameIgnoreCase(departmentName);
    }

    public Map<DegreeName, Long> countEachDegreeInDepartment(Department department) {
        return department.getLectors().stream()
                .collect(Collectors.groupingBy(lector -> lector.getDegree().getName(), Collectors.counting()));
    }

    public OptionalDouble calculateAverageSalary(Department department) {
        return department.getLectors().stream()
                .mapToInt(Lector::getSalary)
                .average();
    }
}
