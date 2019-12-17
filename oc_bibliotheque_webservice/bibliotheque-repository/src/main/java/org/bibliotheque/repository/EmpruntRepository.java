package org.bibliotheque.repository;

import org.bibliotheque.entity.EmpruntEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EmpruntRepository extends CrudRepository<EmpruntEntity, Integer> {

   List<EmpruntEntity> findAllByCompteId(Integer compte_id);

   @Query("SELECT o FROM EmpruntEntity o WHERE livre_id = :x")
   List<EmpruntEntity> findAllEmpruntByOuvrageId(@Param("x") Integer livre_id);
}
