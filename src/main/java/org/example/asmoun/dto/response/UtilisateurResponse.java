package org.example.asmoun.dto.response;

import org.example.asmoun.enums.Role;

public record UtilisateurResponse(
        Long id ,
        String nom ,
        String prenom ,
        String email,
        String tele,
        Role role,
        String numeropermis) {}
