package org.example.asmoun.dto.request;

import jakarta.validation.constraints.*;


public record VehiculeCreateRequest (
        @NotBlank String marque,
        @NotBlank String modele,
        @NotBlank String immatriculation,
        @NotNull @Min(1) @Max(6) Integer capacite
) {}
