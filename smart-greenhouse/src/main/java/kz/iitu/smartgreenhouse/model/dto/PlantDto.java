package kz.iitu.smartgreenhouse.model.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PlantDto {

    private Long id;
    private String name;
    private String description;

    private String imageUrl;
    private Float minimumTemperature;

    private Float maximumTemperature;

    private Float minimumHumidityAir;

    private Float maximumHumidityAir;

    private Float minimumHumidityGround;

    private Float maximumHumidityGround;

    private Float minimumLight;

    private Float maximumLight;

    private Float minimumCarbonDioxide;

    private Float maximumCarbonDioxide;


}
