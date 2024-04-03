package kz.iitu.smartgreenhouse.repository;

import kz.iitu.smartgreenhouse.model.Greenhouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GreenhouseRepository extends JpaRepository<Greenhouse,Long> {
    List<Greenhouse> findAllByOwnerId(Long id);

    List<Greenhouse> findByOwner_IdOrderByNameAsc(Long id);
}
