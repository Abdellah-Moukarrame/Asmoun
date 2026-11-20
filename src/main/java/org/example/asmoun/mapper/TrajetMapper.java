package org.example.asmoun.mapper;

import org.example.asmoun.dto.request.TrajetCreateRequest;
import org.example.asmoun.dto.response.TrajetResponse;
import org.example.asmoun.entity.Trajet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TrajetMapper {
    @Mapping(target = "id",ignore=true)
    @Mapping(target="vehicule",ignore = true)
    @Mapping(target = "statut",ignore = true)
    @Mapping(target = "pointArret", source = "pointsArret")
    Trajet toEntity (TrajetCreateRequest request);
    @Mapping(target = "vehiculeId", source = "vehicule.id")
    @Mapping(target = "conducteurId",source = "vehicule.conducteur.id")
    @Mapping(target = "pointsArret", source = "pointArret")
    TrajetResponse toResponse(Trajet trajet);
    
}
