package kz.iitu.smartgreenhouse.repository;

import kz.iitu.smartgreenhouse.model.Notifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationsRepository extends JpaRepository<Notifications,Long> {
    Optional<Notifications> findByArduinoId(Long id);
}
