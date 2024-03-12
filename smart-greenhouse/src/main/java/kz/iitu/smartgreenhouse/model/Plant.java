package kz.iitu.smartgreenhouse.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "plants")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Plant implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;


    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "const_temperature")
    private Float temperature;

    @Column(name = "const_humidity")
    private Float humidity;

    @Column(name = "const_carbon_dioxide")
    private Float carbonDioxide;

    //TODO:IMAGE ->
    // Getters and setters

}