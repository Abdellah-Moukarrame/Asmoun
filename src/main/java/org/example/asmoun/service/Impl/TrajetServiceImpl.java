package org.example.asmoun.service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.asmoun.dto.request.PointArretCreateRequest;
import org.example.asmoun.dto.request.TrajetCreateRequest;
import org.example.asmoun.dto.request.TrajetSearchRequest;
import org.example.asmoun.dto.response.TrajetResponse;
import org.example.asmoun.entity.PointArret;
import org.example.asmoun.entity.Trajet;
import org.example.asmoun.entity.Vehicule;

import org.example.asmoun.enums.StatutTrajet;
import org.example.asmoun.mapper.TrajetMapper;
import org.example.asmoun.repository.TrajetRepository;
import org.example.asmoun.repository.VehiculeRepository;
import org.example.asmoun.service.TrajetService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor

public class TrajetServiceImpl implements TrajetService {
    private  final TrajetRepository trajetRepository;
    private final VehiculeRepository vehiculeRepository;
    private final TrajetMapper trajetMapper ;
    @Override
    @Transactional
    public TrajetResponse createTrajet(Long conducteurId, TrajetCreateRequest request) {
        Vehicule vehicule = vehiculeRepository.findByIdAndConducteurId(request.vehiculeId(),conducteurId).orElseThrow(()->new IllegalArgumentException("vehicule not found to this driver "));

        verifierPointsArret(request.pointsArret());
        Trajet trajet =  trajetMapper.toEntity(request);
        trajet.setVehicule(vehicule);
        trajet.setNombrePlaces(vehicule.getCapacite() - 1); //-1 blast chauffeur
        trajet.setStatut(StatutTrajet.PUBLIE);
        trajet.getPointArret().sort(Comparator.comparing(PointArret::getOrdre));
        trajet.getPointArret().forEach(pointArret -> {
            pointArret.setVille(pointArret.getVille().trim());
            pointArret.setTrajet(trajet);
        });
        Trajet saved = trajetRepository.save(trajet);
        return trajetMapper.toResponse(saved);

    }


    @Override
    @Transactional(readOnly = true)
    public Page<TrajetResponse> searchTrajet(TrajetSearchRequest request, Pageable pageable) {
       return trajetRepository.searchTrajet(
               request.villDeDepart(),
               request.villDeDepart(),
               request.dateDepart(),
               request.nombrePlaces(),
               request.prixMax(),
               pageable
       ).map(trajetMapper::toResponse);

    }
    public void verifierPointsArret(List<PointArretCreateRequest>points){
        if (points == null || points.size()<2) {
            throw new IllegalArgumentException("the trajet must contains 2 stop points at least ");
        }
        List<PointArretCreateRequest>pointsTries = points.stream().sorted(Comparator.comparing(PointArretCreateRequest::ordre)).toList();
        PointArretCreateRequest premierPoint = pointsTries.get(0);
        if (!premierPoint.heurePassage().isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("the start date must be in the furtur ");
        }
        Set<String> villes = new HashSet<>();
        for (int i = 0; i < pointsTries.size(); i++) {
            PointArretCreateRequest actual = pointsTries.get(i);
            if(actual.ordre()!=i){
                throw new IllegalArgumentException("the ordre must start from zero and be continued");
            }
            String ville = actual.ville().trim().toLowerCase(Locale.ROOT);
            if (!villes.add(ville)) {
                throw new IllegalArgumentException("Trajet must not contains the same city twice");
            }
            if (i>0){
                PointArretCreateRequest precedentPoint = pointsTries.get(i-1);

                if (!actual.heurePassage().isAfter((precedentPoint.heurePassage()))) {
                    throw new IllegalArgumentException("l heure de passage il faut etre croissant");
                }
                if (actual.prixDepuisDepart()
                        .compareTo(premierPoint.prixDepuisDepart()) <= 0) {
                    throw new IllegalArgumentException(
                            "Les prix doivent être strictement croissants"
                    );
                }
            }

        }



    }
}
