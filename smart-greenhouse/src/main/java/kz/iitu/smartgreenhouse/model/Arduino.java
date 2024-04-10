package kz.iitu.smartgreenhouse.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "arduinos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Arduino implements Serializable {
    @Id
    private Long id;

    private Float temperature;

    private Float humidity;

    private Float light;

    @Column(name = "carbon_dioxide")
    private Float carbonDioxide;

    @ManyToOne
    @JoinColumn(name = "plant_id")
    private Plant plant;
}
