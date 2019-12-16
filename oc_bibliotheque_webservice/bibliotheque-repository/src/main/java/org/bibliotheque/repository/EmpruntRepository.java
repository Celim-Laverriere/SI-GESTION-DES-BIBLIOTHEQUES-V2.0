package org.bibliotheque.repository;

import org.bibliotheque.entity.EmpruntEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EmpruntRepository extends CrudRepository<EmpruntEntity, Integer> {

   List<EmpruntEntity> findAllByCompteId(Integer compte_id);

}
