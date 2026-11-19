package org.example.asmoun.service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.asmoun.dto.request.VehiculeCreateRequest;
import org.example.asmoun.dto.request.VehiculeUpdateRequest;
import org.example.asmoun.dto.response.VehiculeResponse;
import org.example.asmoun.entity.Conducteur;
import org.example.asmoun.entity.Utilisateur;
import org.example.asmoun.entity.Vehicule;
import org.example.asmoun.mapper.VehiculeMapper;
import org.example.asmoun.repository.UtilisateurRepository;
import org.example.asmoun.repository.VehiculeRepository;
import org.example.asmoun.service.VehiculeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl implements VehiculeService {
    private final VehiculeRepository vehiculeRepository ;
    private final UtilisateurRepository utilisateurRepository ;
    private final VehiculeMapper vehiculeMapper ;
    @Override
    @Transactional
    public VehiculeResponse createVehicule(Long conducteurId, VehiculeCreateRequest request) {
        Conducteur conducteur = getConducteur(conducteurId) ;
        Vehicule vehicule = vehiculeMapper.toEntityu(request);
        vehicule.setConducteur(conducteur);

        Vehicule saved = vehiculeRepository.save(vehicule);
        return vehiculeMapper.toResonse(saved);
    }

    @Override
    public VehiculeResponse getVehiculeById(Long id) {
        Vehicule vehicule = vehiculeRepository.findById(id).orElseThrow(()->new IllegalArgumentException("no user found"));
        return vehiculeMapper.toResonse(vehicule);
    }

    @Override
    public List<VehiculeResponse> getVehiculesByCondictureId(Long conducteurId) {
        return List.of();
    }

    @Override
    @Transactional
    public VehiculeResponse updateVehicule(Long id, Long conducteurId, VehiculeUpdateRequest request) {

        getConducteur(conducteurId);
        Vehicule vehicule = vehiculeRepository.findByIdAndConducteurId(id,conducteurId).orElseThrow(()->new IllegalArgumentException("no vehicule found with this driver id"));
        vehiculeMapper.updateEntity(request,vehicule);
        Vehicule saved = vehiculeRepository.save(vehicule);
        return vehiculeMapper.toResonse(saved);
    }

    @Override
    public void deleteVehicule(Long id, Long conducteurId) {

    }

    private Conducteur getConducteur(Long conducteurId ){
        Utilisateur utilisateur = utilisateurRepository.findById(conducteurId).orElseThrow(()->new IllegalArgumentException("no user found"));
        if (!(utilisateur instanceof Conducteur conducteur)) {
            throw new IllegalArgumentException("u can do this u are not a driver");
        }
        return conducteur ;
    }
}
