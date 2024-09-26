package kriach.bot.repositories;

import kriach.bot.entities.Degree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface DegreeRepository extends JpaRepository<Degree, BigInteger> {
}
