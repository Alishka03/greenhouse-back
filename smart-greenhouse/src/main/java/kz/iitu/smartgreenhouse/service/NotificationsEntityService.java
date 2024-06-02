package kz.iitu.smartgreenhouse.service;

import kz.iitu.smartgreenhouse.model.Notifications;
import kz.iitu.smartgreenhouse.repository.NotificationsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class NotificationsEntityService {
    private final NotificationsRepository repository;

    public NotificationsEntityService(NotificationsRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Notifications save(Notifications notifications){
        return repository.save(notifications);
    }

    @Transactional(readOnly = true)
    public Optional<Notifications> findByArduinoId(Long id){
        return repository.findByArduinoId(id);
    }
}
