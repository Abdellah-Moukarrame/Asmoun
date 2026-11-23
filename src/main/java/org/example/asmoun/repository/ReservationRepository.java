package org.example.asmoun.repository;

import org.example.asmoun.entity.Reservation;
import org.example.asmoun.enums.StatutReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    List<Reservation> findByPassagerId(Long passagerId);
    Optional<Reservation> findByIdAndPassagerId(Long id, Long passagerId);
    List<Reservation> findByTrajetId(Long trajetId);
    List<Reservation> findByTrajetIdAndStatut(Long trajetId, StatutReservation statut);
    boolean existsByPassagerIdAndTrajetIdAndStatut(Long passagerId,Long trajetId,StatutReservation statut);

}