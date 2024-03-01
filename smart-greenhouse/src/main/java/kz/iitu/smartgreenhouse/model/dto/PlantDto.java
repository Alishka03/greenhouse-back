package kz.iitu.smartgreenhouse.model.dto;

import jakarta.persistence.Column;

public class PlantDto {

    private Long id;
    private String name;
    private String description;

    private String imageUrl;
    private Float temperature;

    private Float humidity;

    private Float carbonDioxide;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
}
