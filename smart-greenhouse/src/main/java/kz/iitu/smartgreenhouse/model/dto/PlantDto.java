package kz.iitu.smartgreenhouse.model.dto;

import jakarta.persistence.Column;


public class PlantDto {

    private Long id;
    private String name;
    private String description;

    private String imageUrl;
    private Float minimumTemperature;

    private Float maximumTemperature;

    private Float minimumHumidity;

    private Float maximumHumidity;

    private Float minimumLight;

    private Float maximumLight;

    private Float minimumCarbonDioxide;

    private Float maximumCarbonDioxide;

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

    public Float getMinimumTemperature() {
        return minimumTemperature;
    }

    public void setMinimumTemperature(Float minimumTemperature) {
        this.minimumTemperature = minimumTemperature;
    }

    public Float getMaximumTemperature() {
        return maximumTemperature;
    }

    public void setMaximumTemperature(Float maximumTemperature) {
        this.maximumTemperature = maximumTemperature;
    }

    public Float getMinimumHumidity() {
        return minimumHumidity;
    }

    public void setMinimumHumidity(Float minimumHumidity) {
        this.minimumHumidity = minimumHumidity;
    }

    public Float getMaximumHumidity() {
        return maximumHumidity;
    }

    public void setMaximumHumidity(Float maximumHumidity) {
        this.maximumHumidity = maximumHumidity;
    }

    public Float getMinimumLight() {
        return minimumLight;
    }

    public void setMinimumLight(Float minimumLight) {
        this.minimumLight = minimumLight;
    }

    public Float getMaximumLight() {
        return maximumLight;
    }

    public void setMaximumLight(Float maximumLight) {
        this.maximumLight = maximumLight;
    }

    public Float getMinimumCarbonDioxide() {
        return minimumCarbonDioxide;
    }

    public void setMinimumCarbonDioxide(Float minimumCarbonDioxide) {
        this.minimumCarbonDioxide = minimumCarbonDioxide;
    }

    public Float getMaximumCarbonDioxide() {
        return maximumCarbonDioxide;
    }

    public void setMaximumCarbonDioxide(Float maximumCarbonDioxide) {
        this.maximumCarbonDioxide = maximumCarbonDioxide;
    }
}
