package kz.iitu.smartgreenhouse.repository;

import kz.iitu.smartgreenhouse.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    @Query("SELECT u FROM User u JOIN u.greenhouses g JOIN g.arduino a WHERE a.id = :arduinoId")
    Optional<User> findByArduinoId(Long arduinoId);
}
