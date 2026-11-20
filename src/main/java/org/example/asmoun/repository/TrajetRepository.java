package org.example.asmoun.repository;

import org.example.asmoun.entity.Trajet;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TrajetRepository extends JpaRepository<Trajet,Long> {
    List<Trajet> findByVehiculeConducteurId(Long conducteurId);
    Optional<Trajet> findByIdAndVehiculeConducteurId(Long id, Long conducteurId);
    boolean existsByVehiculeId(Long vehiculeId);

    @Query("""
            select t from Trajet t 
            join t.pointArret depart  
            join t.pointArret arrivee
            where depart.ordre<arrivee.ordre
            and(:villDeDepart is null or lower(depart.ville)=lower(:villDeDepart)
            and(:villeDarrive is null or lower(arrivee.ville)=lower(:villeDarrive)
            and(:dateDepart is null or depart.heurePassage>= :dateDepart)
            and(:prixMax is null or (arrivee.prixDepuisDepart - depart.prixDepuisDepart)<= :prixMax)
                       """

    )
    Page<Trajet> searchTrajet(
            @Param("villDeDepart") String villDeDepart,
            @Param("villeDarrive") String villeDarrive ,
            @Param("dateDepart")LocalDateTime dateDepart ,
            @Param("nombrePlaces") Integer nombrePlaces ,
            @Param("prixMax")BigDecimal prixMax,
            Pageable pageable
            );
}
