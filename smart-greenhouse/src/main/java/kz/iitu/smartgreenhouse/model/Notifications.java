package kz.iitu.smartgreenhouse.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notifications {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "optimal_temperature")
    private Boolean optimalTemperature;
    @Column(name = "optimal_humidity_air")
    private Boolean optimalHumidityAir;
    @Column(name = "optimal_humidity_ground")
    private Boolean optimalHumidityGround;
    @Column(name = "optimal_light")
    private Boolean optimalLight;
    @Column(name = "optimal_carbon_dioxide")
    private Boolean optimalCarbonDioxide;
    @OneToOne
    @JoinColumn(name = "arduino_id")
    private Arduino arduino;

}
