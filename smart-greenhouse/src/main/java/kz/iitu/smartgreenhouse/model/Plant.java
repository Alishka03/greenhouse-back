package kz.iitu.smartgreenhouse.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "plants")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Plant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "breakpoint_id")
    private BreakPoint breakpoint;

    // Getters and setters

}