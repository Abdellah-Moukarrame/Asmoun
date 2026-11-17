package org.example.asmoun.mapper;

import org.example.asmoun.dto.response.UtilisateurResponse;
import org.example.asmoun.entity.Conducteur;
import org.example.asmoun.entity.Utilisateur;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.context.annotation.Bean;

@Mapper(componentModel = "spring")

public interface UtilisateurMapper {
    @Mapping(target = "numeropermis",ignore=true)
    UtilisateurResponse utilisateurToResponse(Utilisateur utilisateur);
    UtilisateurResponse conducteurToResponse(Conducteur conducteur);
    default UtilisateurResponse toResponse(Utilisateur utilisateur){
        if (utilisateur == null) {
            return null ;
        }
        if (utilisateur instanceof Conducteur conducteur) {
            return conducteurToResponse(conducteur);
        }
        return utilisateurToResponse(utilisateur);
    }
}
