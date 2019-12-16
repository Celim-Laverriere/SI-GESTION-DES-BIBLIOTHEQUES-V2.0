package org.bibliotheque.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "livre")
@Getter @Setter
@NoArgsConstructor
public class LivreEntity implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "ref_bibliotheque")
    private String refBibliotheque;
    private String statut;
    @Column(name = "ouvrage_id")
    private Integer ouvrageId;

    @ManyToOne
    @JoinColumn(name = "ouvrage_id", insertable = false, updatable = false)
    private OuvrageEntity ouvrageLivre;

}
