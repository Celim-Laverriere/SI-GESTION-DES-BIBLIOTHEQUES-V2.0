package org.bibliotheque.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "ouvrage")
@Getter @Setter
@NoArgsConstructor
public class OuvrageEntity implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String titre;
    private String genre;
    private String resumer;
    private String auteur;
    private String editeur;
    private String ref;

    @OneToMany(mappedBy = "ouvragePhoto", fetch = FetchType.LAZY)
    private List<PhotoEntity> photos;

    @OneToMany(mappedBy = "ouvrageLivre", fetch = FetchType.LAZY)
    private List<LivreEntity> livres;

}
