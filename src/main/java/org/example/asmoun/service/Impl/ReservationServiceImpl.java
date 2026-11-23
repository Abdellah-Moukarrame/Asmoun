package org.example.asmoun.service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.asmoun.dto.request.ReservationCreateRequest;
import org.example.asmoun.dto.response.ReservationResponse;
import org.example.asmoun.entity.Passager;
import org.example.asmoun.entity.PointArret;
import org.example.asmoun.entity.Reservation;
import org.example.asmoun.entity.Trajet;
import org.example.asmoun.entity.Utilisateur;
import org.example.asmoun.enums.StatutReservation;
import org.example.asmoun.enums.StatutTrajet;
import org.example.asmoun.exception.InvalidValueException;
import org.example.asmoun.exception.RequiredArgumentException;
import org.example.asmoun.exception.ResourceAlreadyExistException;
import org.example.asmoun.exception.ResourceNotFoundException;
import org.example.asmoun.mapper.ReservationMapper;
import org.example.asmoun.repository.ReservationRepository;
import org.example.asmoun.repository.TrajetRepository;
import org.example.asmoun.repository.UtilisateurRepository;
import org.example.asmoun.service.ReservationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final TrajetRepository trajetRepository;
    private final ReservationMapper reservationMapper;

    @Override
    @Transactional
    public ReservationResponse createReservation(
            Long passagerId,
            ReservationCreateRequest request
    ) {
        if (request == null) {
            throw new RequiredArgumentException("Reservation request is required");
        }

        Passager passager = getPassager(passagerId);

        Trajet trajet = trajetRepository.findByIdForUpdate(request.trajetId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Trip not found")
                );

        if (trajet.getStatut() != StatutTrajet.PUBLIE) {
            throw new InvalidValueException("This trip is not available for booking");
        }

        PointArret pointMontee = trouverPointArret(
                trajet,
                request.pointMonteeId()
        );

        PointArret pointDescente = trouverPointArret(
                trajet,
                request.pointDescenteId()
        );

        if (pointMontee.getOrdre() >= pointDescente.getOrdre()) {
            throw new InvalidValueException(
                    "The boarding stop must come before the drop-off stop"
            );
        }

        boolean reservationExistante =
                reservationRepository.existsByPassagerIdAndTrajetIdAndStatut(
                        passagerId,
                        trajet.getId(),
                        StatutReservation.CONFIRMEE
                );

        if (reservationExistante) {
            throw new ResourceAlreadyExistException(
                    "This passenger already has a confirmed reservation for this trip"
            );
        }

        int placesReservees = reservationRepository
                .findByTrajetIdAndStatut(
                        trajet.getId(),
                        StatutReservation.CONFIRMEE
                )
                .stream()
                .mapToInt(Reservation::getNombrePlaces)
                .sum();

        int placesDisponibles = trajet.getNombrePlaces() - placesReservees;

        if (request.nombrePlaces() > placesDisponibles) {
            throw new InvalidValueException(
                    "Not enough seats are available"
            );
        }

        BigDecimal prixParPlace = pointDescente.getPrixDepuisDepart()
                .subtract(pointMontee.getPrixDepuisDepart());

        BigDecimal montantTotal = prixParPlace.multiply(
                BigDecimal.valueOf(request.nombrePlaces())
        );

        Reservation reservation = reservationMapper.toEntity(request);
        reservation.setPassager(passager);
        reservation.setTrajet(trajet);
        reservation.setPointMontee(pointMontee);
        reservation.setPointDescente(pointDescente);
        reservation.setMontantTotal(montantTotal);
        reservation.setStatut(StatutReservation.CONFIRMEE);

        Reservation savedReservation = reservationRepository.save(reservation);

        return reservationMapper.toResponse(savedReservation);
    }

    @Override
    @Transactional(readOnly = true)
    public ReservationResponse getReservationById(Long id, Long passagerId) {
        Reservation reservation = reservationRepository
                .findByIdAndPassagerId(id, passagerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Reservation not found for this passenger"
                        )
                );

        return reservationMapper.toResponse(reservation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationResponse> getReservationsByPassager(Long passagerId) {
        getPassager(passagerId);

        return reservationRepository.findByPassagerId(passagerId)
                .stream()
                .map(reservationMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationResponse> getReservationsByTrajet(
            Long trajetId,
            Long conducteurId
    ) {
        trajetRepository.findByIdAndVehiculeConducteurId(
                        trajetId,
                        conducteurId
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Trip not found for this driver"
                        )
                );

        return reservationRepository.findByTrajetId(trajetId)
                .stream()
                .map(reservationMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public ReservationResponse annulerReservation(Long id, Long passagerId) {
        Reservation reservation = reservationRepository
                .findByIdAndPassagerId(id, passagerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Reservation not found for this passenger"
                        )
                );

        if (reservation.getStatut() == StatutReservation.ANNULEE) {
            throw new InvalidValueException(
                    "This reservation has already been cancelled"
            );
        }

        reservation.setStatut(StatutReservation.ANNULEE);

        Reservation savedReservation = reservationRepository.save(reservation);

        return reservationMapper.toResponse(savedReservation);
    }

    private Passager getPassager(Long passagerId) {
        Utilisateur utilisateur = utilisateurRepository.findById(passagerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Passenger not found")
                );

        if (!(utilisateur instanceof Passager passager)) {
            throw new InvalidValueException(
                    "This user is not a passenger"
            );
        }

        return passager;
    }

    private PointArret trouverPointArret(Trajet trajet, Long pointArretId) {
        return trajet.getPointArret()
                .stream()
                .filter(point -> point.getId().equals(pointArretId))
                .findFirst()
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Stop point not found for this trip"
                        )
                );
    }


}