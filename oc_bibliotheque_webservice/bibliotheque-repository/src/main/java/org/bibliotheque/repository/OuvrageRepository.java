package org.bibliotheque.repository;

import org.bibliotheque.entity.OuvrageEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OuvrageRepository extends CrudRepository<OuvrageEntity, Integer> {

    @Query("SELECT o FROM OuvrageEntity o WHERE CONCAT (auteur, titre) LIKE :x")
    List<OuvrageEntity> findAllOuvragesByKeyword(@Param("x") String keyword);

}
