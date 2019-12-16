package org.bibliotheque.service.contract;

import org.bibliotheque.entity.OuvrageEntity;
import java.util.List;

public interface OuvrageService {

    OuvrageEntity getOuvrageById(Integer id);

    List<OuvrageEntity> getAllOuvrages();

    OuvrageEntity addOuvrage(OuvrageEntity ouvrage);

    boolean updateOuvrage(OuvrageEntity ouvrage);

    boolean deleteOuvrage(Integer id);

    List<OuvrageEntity> getAllOuvagesByKeyword(String keyword);

}
