package org.example.asmoun.mapper;

import org.example.asmoun.dto.request.PointArretCreateRequest;
import org.example.asmoun.dto.response.PointArretResponse;
import org.example.asmoun.entity.PointArret;
import org.example.asmoun.enums.StatutReservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PointArretMapper {
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "trajet",ignore = true)
    PointArret toEntity(PointArretCreateRequest request);
    PointArretResponse toResponse(PointArret pointArret);
}
