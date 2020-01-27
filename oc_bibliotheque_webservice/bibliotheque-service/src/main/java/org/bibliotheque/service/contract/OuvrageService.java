package org.bibliotheque.service.contract;

import org.bibliotheque.entity.OuvrageEntity;
import java.util.List;

public interface OuvrageService {

    OuvrageEntity getOuvrageById(Integer id);

    List<OuvrageEntity> getAllOuvrages();

    OuvrageEntity addOuvrage(OuvrageEntity ouvrage);

    void updateOuvrage(OuvrageEntity ouvrage);

    void deleteOuvrage(Integer id);

    List<OuvrageEntity> getAllOuvagesByKeyword(String keyword);

}
