package org.example.asmoun.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.asmoun.enums.StatutTrajet;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "trajets")
@Getter
@Setter
@NoArgsConstructor
public class Trajet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    @Column(name = "nombre_places", nullable = false)
    private Integer nombrePlaces;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false)
    private StatutTrajet statut = StatutTrajet.PUBLIE;
    @ManyToOne(fetch = FetchType.LAZY , optional = false)
    @JoinColumn(name = "vehicule_id", nullable = false)
    private Vehicule vehicule;
    @OneToMany(mappedBy = "trajet",cascade = CascadeType.ALL,orphanRemoval = true)
    @OrderBy("ordre ASC")
    private List<PointArret> pointArret =  new ArrayList<>();
}
