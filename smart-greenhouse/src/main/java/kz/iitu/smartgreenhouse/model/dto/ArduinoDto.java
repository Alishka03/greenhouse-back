package kz.iitu.smartgreenhouse.model.dto;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import kz.iitu.smartgreenhouse.model.Plant;

public class ArduinoDto {

    private Long id;
    private Float temperature;

    private Float humidity;

    private Float light;

    private Float carbonDioxide;

    private PlantDto plant;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public Float getHumidity() {
        return humidity;
    }

    public void setHumidity(Float humidity) {
        this.humidity = humidity;
    }

    public Float getCarbonDioxide() {
        return carbonDioxide;
    }

    public void setCarbonDioxide(Float carbonDioxide) {
        this.carbonDioxide = carbonDioxide;
    }

    public PlantDto getPlant() {
        return plant;
    }

    public void setPlant(PlantDto plant) {
        this.plant = plant;
    }

    public Float getLight() {
        return light;
    }

    public void setLight(Float light) {
        this.light = light;
    }
}
