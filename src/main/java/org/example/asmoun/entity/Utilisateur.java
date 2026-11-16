package org.example.asmoun.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.*;
import org.example.asmoun.enums.Role;


@Getter
@Setter
@Entity
@Table(name = "utilisateurs")
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false)
    private String prenom ;

    @Column(nullable = false)
    private String nom ;

    @Column(nullable = false , unique = true)
    private String email ;

    @Column(nullable = false)
    private String password ;

    @Column(nullable = true)
    private String tele ;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role ;

}
