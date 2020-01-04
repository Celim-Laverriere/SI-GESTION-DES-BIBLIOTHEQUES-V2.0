package org.bibliotheque.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "reservation")
@Getter @Setter
@NoArgsConstructor
public class ReservationEntity implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "date_demande_de_resa")
    private Date dateDemandeDeResa;

    @Column(name = "date_ouvrage_disponible")
    private Date dateOuvrageDisponible;

    @Column(name = "num_position_resa")
    private Integer numPositionResa;

    @Column(name = "statut")
    private String statut;

    @Column(name = "ouvrage_id")
    private Integer ouvrageId;

    @Column(name = "compte_id")
    private Integer compteId;

    @ManyToOne
    @JoinColumn(name = "ouvrage_id", insertable = false, updatable = false)
    private OuvrageEntity ouvrageReservation;

}
