package org.example.asmoun.dto.response;

import org.example.asmoun.enums.StatutReservation;
import org.example.asmoun.enums.StatutTrajet;


import java.util.List;

public record TrajetResponse(
        Long id,
        Integer nombrePlaces,
        StatutTrajet statut,
        Long vehiculeId ,
        Long conducteurId,
        List<PointArretResponse> pointsArret) {
}
