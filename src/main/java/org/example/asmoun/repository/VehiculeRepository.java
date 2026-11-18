package org.example.asmoun.repository;

import org.example.asmoun.entity.Conducteur;
import org.example.asmoun.entity.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VehiculeRepository extends JpaRepository<Vehicule,Long> {
    List<Vehicule> findByConducteurId(Long conducteurId);
    Optional<Vehicule> findByIdAndConducteurId(Long id , Long conducteurId);

}
