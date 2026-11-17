package org.example.asmoun.repository;

import org.example.asmoun.entity.Utilisateur;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur,Long> {
    boolean existsByEmail(String email);
    Optional<Utilisateur> findByEmail (String email);

}
