package kz.iitu.smartgreenhouse.model.dto;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import kz.iitu.smartgreenhouse.model.Notifications;
import kz.iitu.smartgreenhouse.model.Plant;


public class ArduinoDto {

    private Long id;
    private Float temperature;

    private Float humidityAir;

    private Float humidityGround;

    private Float light;

    private Float carbonDioxide;

    private PlantDto plant;

    private Notifications notifications;


    public ArduinoDto() {
    }

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

    public Float getHumidityAir() {
        return humidityAir;
    }

    public void setHumidityAir(Float humidityAir) {
        this.humidityAir = humidityAir;
    }

    public Float getHumidityGround() {
        return humidityGround;
    }

    public void setHumidityGround(Float humidityGround) {
        this.humidityGround = humidityGround;
    }

    public Float getLight() {
        return light;
    }

    public void setLight(Float light) {
        this.light = light;
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

    public Notifications getNotifications() {
        return notifications;
    }

    public void setNotifications(Notifications notifications) {
        this.notifications = notifications;
    }
}
