package kz.iitu.smartgreenhouse.service;

import kz.iitu.smartgreenhouse.model.Arduino;
import kz.iitu.smartgreenhouse.model.Notifications;
import kz.iitu.smartgreenhouse.model.dto.DataDTO;
import kz.iitu.smartgreenhouse.model.dto.Notification;
import kz.iitu.smartgreenhouse.model.dto.NotificationDTO;
import kz.iitu.smartgreenhouse.model.dto.WarningDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class NotificationService {
    private final RestTemplate restTemplate;
    private final NotificationsEntityService service;

    @Value("${firebase.key}")
    private String key;

    @Value("${firebase.url}")
    private String notificationUrl;

    public NotificationService(RestTemplate restTemplate, NotificationsEntityService service) {
        this.restTemplate = restTemplate;
        this.service = service;
    }

    public void sendNotification(String deviceId, WarningDto warning, Arduino arduino) {
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setTo(deviceId);
        String message = warning.generateWarningMessage();
        notificationDTO.setNotification(new Notification("GreenHouse Warning", message));
        notificationDTO.setData(new DataDTO("show_message", "Low temperature"));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "key=" + key);

        // Send notification
        HttpEntity<NotificationDTO> request = new HttpEntity<>(notificationDTO, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                notificationUrl,
                HttpMethod.POST,
                request,
                String.class
        );
        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println("Notification sent successfully.");
        } else {
            System.err.println("Failed to send notification. Response: " + response);
        }
    }


    private boolean compare(WarningDto warning, Notifications notification) {
        return warning.getOptimalLight() == notification.getOptimalLight() &&
                warning.getOptimalCarbonDioxide() == notification.getOptimalCarbonDioxide() &&
                warning.getOptimalHumidityAir() == notification.getOptimalHumidityAir() &&
                warning.getOptimalHumidityGround() == notification.getOptimalHumidityGround() &&
                warning.getOptimalTemperature() == notification.getOptimalTemperature();
    }


}
