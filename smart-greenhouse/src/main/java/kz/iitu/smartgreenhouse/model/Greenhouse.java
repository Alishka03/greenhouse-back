package kz.iitu.smartgreenhouse.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "greenhouses")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Greenhouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "arduino_id")
    private Arduino arduino;
}
