package kz.iitu.smartgreenhouse.repository;

import kz.iitu.smartgreenhouse.model.Arduino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArduinoRepository extends JpaRepository<Arduino,Long> {
}
