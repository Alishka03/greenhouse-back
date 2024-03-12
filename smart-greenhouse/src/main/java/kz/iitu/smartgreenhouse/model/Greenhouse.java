package kz.iitu.smartgreenhouse.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;


@Entity
@Table(name = "greenhouses")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Greenhouse implements Serializable {

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
