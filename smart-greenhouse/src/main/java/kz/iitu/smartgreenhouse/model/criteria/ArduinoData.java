package kz.iitu.smartgreenhouse.model.criteria;

public class ArduinoData {
    private Long id;
    private Float temperature;
    private Float humidity;

    private Float co2;

    private Float light;


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

    public Float getCo2() {
        return co2;
    }

    public void setCo2(Float co2) {
        this.co2 = co2;
    }

    public Float getLight() {
        return light;
    }

    public void setLight(Float light) {
        this.light = light;
    }
}
