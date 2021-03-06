package org.bibliotheque.entity;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "photo")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhotoEntity implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "url_photo")
    private String urlPhoto;
    @Column(name = "nom_photo")
    private String nomPhoto;
    @Column(name = "ouvrage_id")
    private Integer ouvrageId;

    @ManyToOne
    @JoinColumn(name = "ouvrage_id", insertable = false, updatable = false)
    private OuvrageEntity ouvragePhoto;

}
