package org.example.asmoun.dto.response;

import org.example.asmoun.entity.Conducteur;

public record VehiculeResponse(
        Long id,
        String marque,
        String modele,
        String immatriculation,
        Integer capacite,
        Long conducteurId
        ) {}
