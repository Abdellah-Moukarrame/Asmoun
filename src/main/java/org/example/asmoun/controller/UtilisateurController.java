package org.example.asmoun.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.example.asmoun.dto.request.UtilisateurRegisterRequest;
import org.example.asmoun.dto.response.UtilisateurResponse;
import org.example.asmoun.entity.Conducteur;
import org.example.asmoun.enums.Role;
import org.example.asmoun.service.UtilisateurService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/")
public class UtilisateurController {
    private final UtilisateurService utilisateurService ;

    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @PostMapping("driver/register")
    @Operation(summary = "register driver  ")
    public ResponseEntity<UtilisateurResponse> registerConducteur(@Valid @RequestBody UtilisateurRegisterRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(utilisateurService.register(request, Role.CONDUCTEUR));
    }
    @PostMapping("passagers/register")
    @Operation(summary = "Register passager")
    public ResponseEntity<UtilisateurResponse> registerPassager(@Valid @RequestBody UtilisateurRegisterRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(utilisateurService.register(request, Role.PASSAGER));
    }
    @GetMapping("/{id}")
    @Operation(summary = "Get User By His Id ")
    public ResponseEntity<UtilisateurResponse> getUserById(@PathVariable Long id){
        return ResponseEntity.ok(utilisateurService.getUtilisateurById(id));
    }

}
