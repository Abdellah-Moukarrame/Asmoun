package org.example.asmoun.service;

import org.example.asmoun.dto.request.ReservationCreateRequest;
import org.example.asmoun.dto.response.ReservationResponse;

import java.util.List;

public interface ReservationService {

    ReservationResponse createReservation(
            Long passagerId,
            ReservationCreateRequest request
    );

    ReservationResponse getReservationById(
            Long id,
            Long passagerId
    );

    List<ReservationResponse> getReservationsByPassager(
            Long passagerId
    );

    List<ReservationResponse> getReservationsByTrajet(
            Long trajetId,
            Long conducteurId
    );

    ReservationResponse annulerReservation(
            Long id,
            Long passagerId
    );
}