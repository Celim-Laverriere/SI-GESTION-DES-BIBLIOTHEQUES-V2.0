package org.bibliotheque.repository;

import org.bibliotheque.entity.LivreEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivreRepository extends CrudRepository<LivreEntity, Integer> {
}
