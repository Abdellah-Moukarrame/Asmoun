package org.example.asmoun.service.Impl;

import jakarta.transaction.Transactional;
import org.example.asmoun.entity.Conducteur;
import org.example.asmoun.entity.Passager;
import org.example.asmoun.entity.Utilisateur;
import org.example.asmoun.enums.Role;
import org.example.asmoun.mapper.UtilisateurMapper;
import org.example.asmoun.repository.UtilisateurRepository;
import org.example.asmoun.service.UtilisateurService;

import org.example.asmoun.dto.request.*;

import org.example.asmoun.dto.response.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UtilisateurServiceImpl implements UtilisateurService {
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final UtilisateurMapper utilisateurMapper ;
    private final UtilisateurRepository utilisateurRepository ;

    public UtilisateurServiceImpl(UtilisateurMapper utilisateurMapper, UtilisateurRepository utilisateurRepository) {
        this.utilisateurMapper = utilisateurMapper;
        this.utilisateurRepository = utilisateurRepository;
    }



    @Override
    @Transactional
    public UtilisateurResponse register(UtilisateurRegisterRequest request, Role role) {
        if (role != Role.CONDUCTEUR && role !=Role.PASSAGER) {
            throw new IllegalArgumentException("this role hasn't the permission to register");
        }
        if(utilisateurRepository.existsByEmail(request.email())){
            throw new IllegalStateException("this email already exist");
        }
        Utilisateur utilisateur ;
        if(role == Role.CONDUCTEUR ){
            String permis = request.numeroPermis() ;
            if (permis.isBlank() || permis == null){
                throw new IllegalArgumentException("the liscence is required");
            }
            Conducteur conducteur = new Conducteur();
            conducteur.setNumeropermis(permis);
            utilisateur = conducteur ;

        }else{
            utilisateur = new Passager();
        }
        utilisateur.setNom(request.nom());
        utilisateur.setPrenom(request.prenom());
        utilisateur.setEmail(request.email());
        utilisateur.setTele(request.telephone());
        utilisateur.setRole(role);
        utilisateur.setPassword(passwordEncoder.encode(request.password()));

        Utilisateur saved = utilisateurRepository.save(utilisateur);
        return utilisateurMapper.toResponse(saved);
    }

    @Override
    public UtilisateurResponse getUtilisateurById(Long id) {
       Utilisateur utilisateur = utilisateurRepository.findById(id).orElseThrow(()->
               new IllegalArgumentException("no user found with this id try with a correct one :)")
       );
       return utilisateurMapper.toResponse(utilisateur);
    }

    @Override
    public UtilisateurResponse updateUtilisateur(Long id, UtilisateurUpdateRequest request) {
        return null;
    }

    @Override
    public List<UtilisateurResponse> getAllConducteurs() {
        return List.of();
    }

    @Override
    public List<UtilisateurResponse> getAllPassagers() {
        return List.of();
    }
}
