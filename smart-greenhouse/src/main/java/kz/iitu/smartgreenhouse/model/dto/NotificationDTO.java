package kz.iitu.smartgreenhouse.model.dto;

import lombok.*;
import lombok.Data;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {
    private String to;
    private Notification notification;
    private DataDTO data;
}
