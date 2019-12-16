package org.bibliotheque.repository;

import org.bibliotheque.entity.ReservationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReservationRepository extends CrudRepository<ReservationEntity, Integer> {

    List<ReservationEntity> findAllByOuvrageId(Integer ouvrageId);

    List<ReservationEntity> findAllByCompteId(Integer compteId);
}
