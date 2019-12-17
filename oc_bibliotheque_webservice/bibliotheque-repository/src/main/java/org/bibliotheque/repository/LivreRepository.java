package org.bibliotheque.repository;

import org.bibliotheque.entity.LivreEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LivreRepository extends CrudRepository<LivreEntity, Integer> {

    @Query("SELECT o FROM LivreEntity o WHERE ouvrage_id = :x")
    List<LivreEntity> findAllLivreByOuvrageId(@Param("x") Integer ouvrageId);
}
