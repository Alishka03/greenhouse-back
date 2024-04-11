package kz.iitu.smartgreenhouse.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Averages {
    private  float avgTemperature;
    private  float avgCO2;
    private  float avgHumidityAir;
    private  float avgHumidityGround;
    private  float avgLight;


    public Averages(float avgTemperature, float avgCO2, float avgHumidityAir, float avgHumidityGround, float avgLight) {
        this.avgTemperature = avgTemperature;
        this.avgCO2 = avgCO2;
        this.avgHumidityAir = avgHumidityAir;
        this.avgHumidityGround = avgHumidityGround;
        this.avgLight = avgLight;
    }
}
