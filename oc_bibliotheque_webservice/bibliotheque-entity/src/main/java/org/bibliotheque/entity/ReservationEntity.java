package org.bibliotheque.entity;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "reservation")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationEntity implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "date_resa_disponible")
    private Date dateResaDisponible;

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
