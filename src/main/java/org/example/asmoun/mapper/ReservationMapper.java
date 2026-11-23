package org.example.asmoun.mapper;

import org.example.asmoun.dto.request.ReservationCreateRequest;
import org.example.asmoun.dto.response.ReservationResponse;
import org.example.asmoun.entity.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "montantTotal", ignore = true)
    @Mapping(target = "dateReservation", ignore = true)
    @Mapping(target = "statut", ignore = true)
    @Mapping(target = "passager", ignore = true)
    @Mapping(target = "trajet", ignore = true)
    @Mapping(target = "pointMontee", ignore = true)
    @Mapping(target = "pointDescente", ignore = true)
    Reservation toEntity(ReservationCreateRequest request);

    @Mapping(target = "passagerId", source = "passager.id")
    @Mapping(target = "trajetId", source = "trajet.id")
    @Mapping(target = "pointMonteeId", source = "pointMontee.id")
    @Mapping(target = "pointDescenteId", source = "pointDescente.id")
    ReservationResponse toResponse(Reservation reservation);
}