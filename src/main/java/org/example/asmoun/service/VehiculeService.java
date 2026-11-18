package org.example.asmoun.service;

import org.example.asmoun.dto.request.VehiculeCreateRequest;
import org.example.asmoun.dto.request.VehiculeUpdateRequest;
import org.example.asmoun.dto.response.VehiculeResponse;

import java.util.List;

public interface VehiculeService {
    VehiculeResponse createVehicule(Long conducteurId , VehiculeCreateRequest request);
    VehiculeResponse getVehiculeById(Long id);
    List<VehiculeResponse> getVehiculesByCondictureId(Long conducteurId);
    VehiculeResponse updateVehicule(Long id , Long conducteurId , VehiculeUpdateRequest request);
    void deleteVehicule(Long id , Long conducteurId) ;
}
