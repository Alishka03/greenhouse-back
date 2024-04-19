package kz.iitu.smartgreenhouse.service;

import kz.iitu.smartgreenhouse.model.Notifications;
import kz.iitu.smartgreenhouse.repository.NotificationsRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NotificationsEntityService {
    private final NotificationsRepository repository;

    public NotificationsEntityService(NotificationsRepository repository) {
        this.repository = repository;
    }

    public Notifications save(Notifications notifications){
        return repository.save(notifications);
    }

    public Optional<Notifications> findByArduinoId(Long id){
        return repository.findByArduinoId(id);
    }
}
