package org.example.asmoun.service.Impl;

import org.example.asmoun.dto.request.ReservationCreateRequest;
import org.example.asmoun.dto.response.ReservationResponse;
import org.example.asmoun.entity.Passager;
import org.example.asmoun.entity.PointArret;
import org.example.asmoun.entity.Reservation;
import org.example.asmoun.entity.Trajet;
import org.example.asmoun.entity.Utilisateur;
import org.example.asmoun.enums.StatutReservation;
import org.example.asmoun.enums.StatutTrajet;
import org.example.asmoun.exception.ResourceNotFoundException;
import org.example.asmoun.mapper.ReservationMapper;
import org.example.asmoun.repository.ReservationRepository;
import org.example.asmoun.repository.TrajetRepository;
import org.example.asmoun.repository.UtilisateurRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationServiceImplTest {

    @Mock
    private ReservationMapper reservationMapper;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private UtilisateurRepository utilisateurRepository;

    @Mock
    private TrajetRepository trajetRepository;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    @Test
    void createReservation_shouldReturnResponse_whenRequestIsValid() {
        Long passagerId = 1L;
        Long trajetId = 10L;

        ReservationCreateRequest request =
                new ReservationCreateRequest(trajetId, 20L, 30L, 2);

        Passager passager = new Passager();

        Trajet trajet = new Trajet();
        trajet.setId(trajetId);
        trajet.setNombrePlaces(4);
        trajet.setStatut(StatutTrajet.PUBLIE);

        PointArret depart = new PointArret();
        depart.setId(20L);
        depart.setOrdre(1);
        depart.setPrixDepuisDepart(BigDecimal.ZERO);

        PointArret arrivee = new PointArret();
        arrivee.setId(30L);
        arrivee.setOrdre(2);
        arrivee.setPrixDepuisDepart(new BigDecimal("50.00"));

        trajet.setPointArret(List.of(depart, arrivee));

        Reservation reservation = new Reservation();
        Reservation savedReservation = new Reservation();

        ReservationResponse expectedResponse = new ReservationResponse(
                100L,
                2,
                new BigDecimal("100.00"),
                LocalDateTime.now(),
                StatutReservation.CONFIRMEE,
                passagerId,
                trajetId,
                20L,
                30L
        );

        when(utilisateurRepository.findById(passagerId))
                .thenReturn(Optional.<Utilisateur>of(passager));
        when(trajetRepository.findByIdForUpdate(trajetId))
                .thenReturn(Optional.of(trajet));
        when(reservationRepository.existsByPassagerIdAndTrajetIdAndStatut(
                passagerId, trajetId, StatutReservation.CONFIRMEE
        )).thenReturn(false);
        when(reservationRepository.findByTrajetIdAndStatut(
                trajetId, StatutReservation.CONFIRMEE
        )).thenReturn(List.of());
        when(reservationMapper.toEntity(request)).thenReturn(reservation);
        when(reservationRepository.save(reservation))
                .thenReturn(savedReservation);
        when(reservationMapper.toResponse(savedReservation))
                .thenReturn(expectedResponse);

        ReservationResponse result =
                reservationService.createReservation(passagerId, request);

        assertEquals(expectedResponse, result);
        verify(utilisateurRepository).findById(passagerId);
        verify(trajetRepository).findByIdForUpdate(trajetId);
        verify(reservationMapper).toEntity(request);
        verify(reservationRepository).save(reservation);
        verify(reservationMapper).toResponse(savedReservation);
    }

    @Test
    void getReservationById_shouldReturnResponse_whenReservationExistsForPassager() {
        Long reservationId = 100L;
        Long passagerId = 1L;

        Reservation reservation = new Reservation();
        ReservationResponse expectedResponse = new ReservationResponse(
                reservationId,
                2,
                new BigDecimal("100.00"),
                LocalDateTime.now(),
                StatutReservation.CONFIRMEE,
                passagerId,
                10L,
                20L,
                30L
        );

        when(reservationRepository.findByIdAndPassagerId(
                reservationId, passagerId
        )).thenReturn(Optional.of(reservation));
        when(reservationMapper.toResponse(reservation))
                .thenReturn(expectedResponse);

        ReservationResponse result =
                reservationService.getReservationById(
                        reservationId, passagerId
                );

        assertEquals(expectedResponse, result);
        verify(reservationRepository)
                .findByIdAndPassagerId(reservationId, passagerId);
        verify(reservationMapper).toResponse(reservation);
    }

    @Test
    void getReservationById_shouldThrowException_whenReservationDoesNotBelongToPassager() {
        Long reservationId = 100L;
        Long passagerId = 1L;

        when(reservationRepository.findByIdAndPassagerId(
                reservationId, passagerId
        )).thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> reservationService.getReservationById(
                        reservationId, passagerId
                )
        );

        verify(reservationMapper, never()).toResponse(any());
    }

    @Test
    void getReservationsByPassager_shouldReturnResponses() {
        Long passagerId = 1L;
        Passager passager = new Passager();

        Reservation reservation = new Reservation();
        ReservationResponse response = new ReservationResponse(
                100L,
                2,
                new BigDecimal("100.00"),
                LocalDateTime.now(),
                StatutReservation.CONFIRMEE,
                passagerId,
                10L,
                20L,
                30L
        );

        when(utilisateurRepository.findById(passagerId))
                .thenReturn(Optional.<Utilisateur>of(passager));
        when(reservationRepository.findByPassagerId(passagerId))
                .thenReturn(List.of(reservation));
        when(reservationMapper.toResponse(reservation))
                .thenReturn(response);

        List<ReservationResponse> result =
                reservationService.getReservationsByPassager(passagerId);

        assertEquals(List.of(response), result);
        verify(utilisateurRepository).findById(passagerId);
        verify(reservationRepository).findByPassagerId(passagerId);
        verify(reservationMapper).toResponse(reservation);
    }

    @Test
    void getReservationsByPassager_shouldReturnEmptyList_whenPassagerHasNoReservations() {
        Long passagerId = 1L;
        Passager passager = new Passager();

        when(utilisateurRepository.findById(passagerId))
                .thenReturn(Optional.<Utilisateur>of(passager));
        when(reservationRepository.findByPassagerId(passagerId))
                .thenReturn(List.of());

        List<ReservationResponse> result =
                reservationService.getReservationsByPassager(passagerId);

        assertEquals(List.of(), result);
        verify(utilisateurRepository).findById(passagerId);
        verify(reservationRepository).findByPassagerId(passagerId);
    }

    @Test
    void getReservationsByTrajet_shouldReturnResponsesForConducteurTrajets() {
        Long trajetId = 10L;
        Long conducteurId = 5L;

        Reservation reservation = new Reservation();
        ReservationResponse response = new ReservationResponse(
                100L,
                2,
                new BigDecimal("100.00"),
                LocalDateTime.now(),
                StatutReservation.CONFIRMEE,
                1L,
                trajetId,
                20L,
                30L
        );

        when(trajetRepository.findByIdAndVehiculeConducteurId(
                trajetId, conducteurId
        )).thenReturn(Optional.of(new Trajet()));
        when(reservationRepository.findByTrajetId(trajetId))
                .thenReturn(List.of(reservation));
        when(reservationMapper.toResponse(reservation))
                .thenReturn(response);

        List<ReservationResponse> result =
                reservationService.getReservationsByTrajet(
                        trajetId, conducteurId
                );

        assertEquals(List.of(response), result);
        verify(trajetRepository)
                .findByIdAndVehiculeConducteurId(trajetId, conducteurId);
        verify(reservationRepository).findByTrajetId(trajetId);
        verify(reservationMapper).toResponse(reservation);
    }

    @Test
    void annulerReservation_shouldReturnCancelledResponse_whenReservationExists() {
        Long reservationId = 100L;
        Long passagerId = 1L;

        Reservation reservation = new Reservation();
        reservation.setStatut(StatutReservation.CONFIRMEE);

        ReservationResponse cancelledResponse = new ReservationResponse(
                reservationId,
                2,
                new BigDecimal("100.00"),
                LocalDateTime.now(),
                StatutReservation.ANNULEE,
                passagerId,
                10L,
                20L,
                30L
        );

        when(reservationRepository.findByIdAndPassagerId(
                reservationId, passagerId
        )).thenReturn(Optional.of(reservation));
        when(reservationRepository.save(reservation))
                .thenReturn(reservation);
        when(reservationMapper.toResponse(reservation))
                .thenReturn(cancelledResponse);

        ReservationResponse result =
                reservationService.annulerReservation(
                        reservationId, passagerId
                );

        assertEquals(StatutReservation.ANNULEE, reservation.getStatut());
        assertEquals(cancelledResponse, result);
        verify(reservationRepository)
                .findByIdAndPassagerId(reservationId, passagerId);
        verify(reservationRepository).save(reservation);
        verify(reservationMapper).toResponse(reservation);
    }

    @Test
    void annulerReservation_shouldThrowException_whenReservationDoesNotBelongToPassager() {
        Long reservationId = 100L;
        Long passagerId = 1L;

        when(reservationRepository.findByIdAndPassagerId(
                reservationId, passagerId
        )).thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> reservationService.annulerReservation(
                        reservationId, passagerId
                )
        );

        verify(reservationRepository, never()).save(any());
        verify(reservationMapper, never()).toResponse(any());
    }
}