package org.example.asmoun.dto.request;

import jakarta.validation.constraints.*;
import org.example.asmoun.enums.Role;

public record UtilisateurRegisterRequest(
        @NotBlank (message = "le nom est obligatoire") String nom,
        @NotBlank (message = "le prenom est obligatoire") String prenom,
        @NotBlank (message = "le email est obligatoire")@Email String email,
        @NotBlank (message = "le password est obligatoire")@Size(min = 8 , message = "8 characteres minumum") String password,
        @NotBlank (message = "le telephone est obligatoire")String telephone,
        String numeroPermis
) {}