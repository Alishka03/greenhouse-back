package kz.iitu.smartgreenhouse.repository;

import kz.iitu.smartgreenhouse.model.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlantRepository extends JpaRepository<Plant,Long> {
}
