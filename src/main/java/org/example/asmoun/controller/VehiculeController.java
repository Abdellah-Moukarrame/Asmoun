package org.example.asmoun.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.example.asmoun.dto.request.VehiculeCreateRequest;
import org.example.asmoun.dto.request.VehiculeUpdateRequest;
import org.example.asmoun.dto.response.VehiculeResponse;
import org.example.asmoun.service.VehiculeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/conducteur/{conducteurId}/vehicule/")
public class VehiculeController {
    private final VehiculeService vehiculeService;

    public VehiculeController(VehiculeService vehiculeService) {

        this.vehiculeService = vehiculeService;
    }

    @PostMapping("create/")
    @Operation(summary = "create a vehicule")
    public ResponseEntity<VehiculeResponse>createVehicule(@PathVariable Long conducteurId , @Valid @RequestBody VehiculeCreateRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(vehiculeService.createVehicule(conducteurId , request));
    }
    @GetMapping("/{id}")
    @Operation(summary = "Get a vehicule infos by id")
    public ResponseEntity<VehiculeResponse> getVehiculeById(@PathVariable Long id){
        return ResponseEntity.ok(vehiculeService.getVehiculeById(id));
    }
    @PutMapping("update/{id}")
    @Operation(summary = "update a vehicule")
    public ResponseEntity<VehiculeResponse> updateVehicule(@PathVariable Long conducteurId ,@PathVariable Long id, @Valid @RequestBody VehiculeUpdateRequest request){
        return ResponseEntity.ok(vehiculeService.updateVehicule(id,conducteurId,request));
    }
}
