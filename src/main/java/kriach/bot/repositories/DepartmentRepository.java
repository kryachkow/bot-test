package kriach.bot.repositories;

import kriach.bot.entities.Department;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, BigInteger> {

    @EntityGraph(attributePaths = {
            "head",
            "lectors",
            "lectors.degree"
    })
    Optional<Department> findByNameIgnoreCase(String name);
}
