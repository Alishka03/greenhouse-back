package kz.iitu.smartgreenhouse.model.dto;

import kz.iitu.smartgreenhouse.model.User;

public class GreenhouseDto {
    private Long id;
    private String name;
    private User owner;

    private ArduinoDto arduino;

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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public ArduinoDto getArduino() {
        return arduino;
    }

    public void setArduino(ArduinoDto arduino) {
        this.arduino = arduino;
    }
}
