package kz.iitu.smartgreenhouse.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoggerELK {
    private Long arduinoId;
    private Float temperature;
    private Float co2;
    private Float humidityAir;
    private Float humidityGround;
    private Float light;
    private LocalDateTime dateTime;
}
