package org.example.asmoun.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.asmoun.enums.StatutReservation;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
@Getter
@Setter
@NoArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_places", nullable = false)
    private Integer nombrePlaces;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal montantTotal;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dateReservation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutReservation statut = StatutReservation.CONFIRMEE;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "passager_id", nullable = false)
    private Passager passager;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "trajet_id", nullable = false)
    private Trajet trajet;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "point_montee_id", nullable = false)
    private PointArret pointMontee;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "point_descente_id", nullable = false)
    private PointArret pointDescente;

    @PrePersist
    private void initialiserDateReservation() {
        dateReservation = LocalDateTime.now();
    }
}