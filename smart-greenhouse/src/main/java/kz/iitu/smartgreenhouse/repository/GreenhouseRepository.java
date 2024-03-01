package kz.iitu.smartgreenhouse.repository;

import kz.iitu.smartgreenhouse.model.Greenhouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GreenhouseRepository extends JpaRepository<Greenhouse,Long> {
}
