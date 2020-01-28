package org.bibliotheque.service.contract;

import org.bibliotheque.entity.CompteEntity;
import java.util.List;

public interface CompteService {

    CompteEntity getCompteById(Integer id);

    List<CompteEntity> getAllComptes();

    CompteEntity addCompte(CompteEntity compte);

    void updateCompte(CompteEntity compte);

    void deleteCompte(Integer id);

}
