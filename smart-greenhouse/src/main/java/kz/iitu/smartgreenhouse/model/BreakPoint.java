package kz.iitu.smartgreenhouse.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "breakpoints")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BreakPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "const_temperature")
    private Float temperature;

    @Column(name = "const_humidity")
    private Float humidity;

    @Column(name = "const_carbon_dioxide")
    private Float carbonDioxide;

    // Getters and setters
}
