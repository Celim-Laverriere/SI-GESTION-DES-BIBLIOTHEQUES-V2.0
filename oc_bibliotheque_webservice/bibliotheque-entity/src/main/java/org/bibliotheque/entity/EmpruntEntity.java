package org.bibliotheque.entity;

import java.util.Date;
import lombok.*;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "emprunt")
@Getter @Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class EmpruntEntity implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "date_debut")
    private Date dateDebut;
    @Column(name = "date_fin")
    private Date dateFin;
    @Column(name = "statut")
    private String statut;
    @Column(name = "prolongation")
    private Boolean prolongation;
    @Column(name = "livre_id")
    private Integer livreId;
    @Column(name = "compte_id")
    private Integer compteId;

}
