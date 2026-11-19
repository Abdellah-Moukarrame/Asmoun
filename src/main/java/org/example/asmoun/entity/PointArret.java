package org.example.asmoun.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "points_arret",uniqueConstraints = {
        @UniqueConstraint(
                name = "uk_point_arret_trajet_ordre",
                columnNames = {"trajet_id", "ordre"}
        )
})
@Getter
@Setter
@NoArgsConstructor
public class PointArret {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    @Column(nullable = false)
    private String ville;
    @Column(nullable = false)
    private Integer ordre ;
    @Column(nullable = false)
    private LocalDateTime heurePassage;
    @Column(nullable = false, precision = 10 , scale = 2)
    private BigDecimal prixDepuisDepart;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "trajet_id",nullable = false)
    private Trajet trajet ;

}
