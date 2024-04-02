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

    private Float minimumTemperature;

    private Float maximumTemperature;

    private Float minimumHumidity;

    private Float maximumHumidity;

    private Float minimumLight;

    private Float maximumLight;

    private Float minimumCarbonDioxide;

    private Float maximumCarbonDioxide;

}