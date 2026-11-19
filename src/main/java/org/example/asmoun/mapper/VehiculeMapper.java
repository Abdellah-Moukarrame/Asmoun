package org.example.asmoun.mapper;

import org.example.asmoun.dto.request.VehiculeCreateRequest;
import org.example.asmoun.dto.request.VehiculeUpdateRequest;
import org.example.asmoun.dto.response.VehiculeResponse;
import org.example.asmoun.entity.Vehicule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface VehiculeMapper {
    @Mapping(target = "conducteurId" , source = "conducteur.id")
    VehiculeResponse toResonse(Vehicule vehicule) ;
    @Mapping(target = "id" , ignore = true)
    @Mapping(target = "conducteur" , ignore = true)
    Vehicule toEntityu(VehiculeCreateRequest request);


    @Mapping(target = "id" , ignore = true)
    void updateEntity(VehiculeUpdateRequest request, @MappingTarget Vehicule vehicule);

}
