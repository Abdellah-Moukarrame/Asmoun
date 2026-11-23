package org.example.asmoun.dto.response;

import org.example.asmoun.enums.StatutReservation;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReservationResponse(
        Long id,
        Integer nombrePlaces,
        BigDecimal montantTotal,
        LocalDateTime dateReservation,
        StatutReservation statut,
        Long passagerId,
        Long trajetId,
        Long pointMonteeId,
        Long pointDescenteId
) {
}