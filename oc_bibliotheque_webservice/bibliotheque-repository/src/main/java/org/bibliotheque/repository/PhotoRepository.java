package org.bibliotheque.repository;

import org.bibliotheque.entity.PhotoEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository extends CrudRepository<PhotoEntity, Integer> {
}
