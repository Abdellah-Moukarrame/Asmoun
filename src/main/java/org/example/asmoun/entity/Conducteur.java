package org.example.asmoun.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table (name = "Conducteurs")
@Getter
@Setter
@PrimaryKeyJoinColumn(name = "id")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Conducteur extends Utilisateur {
    @Column(nullable = false, unique = true)
    private String numeropermis ;
}
