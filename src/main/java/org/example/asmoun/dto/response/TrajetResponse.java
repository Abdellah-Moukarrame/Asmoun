package org.example.asmoun.dto.response;

import org.example.asmoun.enums.StatutReservation;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

public record TrajetResponse(
        Long id,
        Integer nombrePlaces,
        StatutReservation statut,
        Long vehiculeId ,
        Long conducteurId,
        List<PointArretResponse> pointsArret) {
}
