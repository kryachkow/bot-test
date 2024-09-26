package kriach.bot.repositories;

import kriach.bot.entities.Lector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface LectorRepository extends JpaRepository<Lector, BigInteger> {

    @Query("select l from Lector l where lower(l.name) LIKE lower(concat('%', :template,'%')) OR lower(l.surname) LIKE lower(concat('%', :template,'%'))")
    List<Lector> getLectorsByTemplate(String template);
}
