package org.example.asmoun.service;

import org.example.asmoun.dto.request.*;
import org.example.asmoun.dto.response.UtilisateurResponse;
import org.example.asmoun.enums.Role;

import java.util.List;

public interface UtilisateurService {

    UtilisateurResponse register(
            UtilisateurRegisterRequest request , Role role
    );

    UtilisateurResponse getUtilisateurById(Long id);

    UtilisateurResponse updateUtilisateur(
            Long id,
            UtilisateurUpdateRequest request
    );

    List<UtilisateurResponse> getAllConducteurs();

    List<UtilisateurResponse> getAllPassagers();
}