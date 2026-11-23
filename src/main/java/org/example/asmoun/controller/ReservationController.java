package org.example.asmoun.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.asmoun.dto.request.ReservationCreateRequest;
import org.example.asmoun.dto.response.ReservationResponse;
import org.example.asmoun.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/passagers/{passagerId}/reservations")
    public ResponseEntity<ReservationResponse> createReservation(
            @PathVariable Long passagerId,
            @Valid @RequestBody ReservationCreateRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reservationService.createReservation(passagerId, request));
    }

    @GetMapping("/passagers/{passagerId}/reservations")
    public ResponseEntity<List<ReservationResponse>> getReservationsByPassager(
            @PathVariable Long passagerId
    ) {
        return ResponseEntity.ok(
                reservationService.getReservationsByPassager(passagerId)
        );
    }

    @GetMapping("/passagers/{passagerId}/reservations/{reservationId}")
    public ResponseEntity<ReservationResponse> getReservationById(
            @PathVariable Long passagerId,
            @PathVariable Long reservationId
    ) {
        return ResponseEntity.ok(
                reservationService.getReservationById(reservationId, passagerId)
        );
    }

    @PatchMapping("/passagers/{passagerId}/reservations/{reservationId}/annuler")
    public ResponseEntity<ReservationResponse> annulerReservation(
            @PathVariable Long passagerId,
            @PathVariable Long reservationId
    ) {
        return ResponseEntity.ok(
                reservationService.annulerReservation(reservationId, passagerId)
        );
    }

    @GetMapping(
            "/conducteurs/{conducteurId}/trajets/{trajetId}/reservations"
    )
    public ResponseEntity<List<ReservationResponse>> getReservationsByTrajet(
            @PathVariable Long conducteurId,
            @PathVariable Long trajetId
    ) {
        return ResponseEntity.ok(
                reservationService.getReservationsByTrajet(trajetId, conducteurId)
        );
    }
}