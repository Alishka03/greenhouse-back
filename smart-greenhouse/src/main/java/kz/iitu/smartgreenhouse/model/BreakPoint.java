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
    private Float temperature;
    private Float humidity;
    @Column(name = "carbon_dioxide")
    private Float carbonDioxide;
}
