package kz.iitu.smartgreenhouse.model.criteria;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ArduinoData {
    private Long id;
    private Float temperature;
    private Float humidityAir;

    private Float humidityGround;

    private Float co2;

    private Float light;


}
