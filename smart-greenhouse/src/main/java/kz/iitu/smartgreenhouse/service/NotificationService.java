package kz.iitu.smartgreenhouse.service;

import kz.iitu.smartgreenhouse.model.dto.DataDTO;
import kz.iitu.smartgreenhouse.model.dto.Notification;
import kz.iitu.smartgreenhouse.model.dto.NotificationDTO;
import kz.iitu.smartgreenhouse.model.dto.WarningDto;
import kz.iitu.smartgreenhouse.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationService {
    private final RestTemplate restTemplate;

    @Value("${firebase.key}")
    private String key;

    @Value("${firebase.url}")
    private String notificationUrl;

    public NotificationService( RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean sendNotification(String deviceId, WarningDto warning) {
        NotificationDTO notificationDTO = new NotificationDTO();

        notificationDTO.setTo(deviceId);
        String message = warning.generateWarningMessage();
        notificationDTO.setNotification(new Notification("GreenHouse Warning",message));
        notificationDTO.setData(new DataDTO("show_message","Low temperature"));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "key=" + key);

        HttpEntity<NotificationDTO> request = new HttpEntity<>(notificationDTO, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                notificationUrl,
                HttpMethod.POST,
                request,
                String.class
        );


        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println("Notification sent successfully.");
            return true;
        } else {
            System.err.println("Failed to send notification. Response: " + response);
            return false;
        }
    }
}
