package org.bibliotheque.repository;

import org.bibliotheque.entity.CompteEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompteRepository extends CrudRepository<CompteEntity, Integer> {

    CompteEntity findByMailAndMotDePasse(String mail, String mot_de_passe);

    CompteEntity findByMail(String mail);
}
