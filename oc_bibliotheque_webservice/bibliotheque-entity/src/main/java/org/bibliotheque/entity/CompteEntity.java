package org.bibliotheque.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "compte")
@Getter @Setter
@NoArgsConstructor
public class CompteEntity implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String nom;
    private String prenom;
    private String adresse;
    @Column(name = "code_postal")
    private Integer codePostal;
    private String ville;
    @Column(name = "num_portable")
    private Integer numPortable;
    @Column(name = "num_domicile")
    private Integer numDomicile;
    @Column(name = "num_carte_bibliotheque")
    private Integer numCarteBibliotheque;
    private String mail;
    @Column(name = "mot_de_passe")
    private String motDePasse;

}
