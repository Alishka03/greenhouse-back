package kz.iitu.smartgreenhouse.model;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "loggering")
public class LoggerEntity {
    @Id
    private String  id;
    private String arduinoId;
    private Float temperature;
    private Float co2;
    private Float humidity;
    private LocalDateTime dateTime;
}
