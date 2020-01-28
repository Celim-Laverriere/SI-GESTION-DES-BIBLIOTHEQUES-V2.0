package org.bibliotheque.service.contract;

import org.bibliotheque.entity.EmpruntEntity;
import java.util.List;

public interface EmpruntService {

    EmpruntEntity getEmpruntById(Integer id);

    List<EmpruntEntity> getAllEmprunts();

    List<EmpruntEntity> getAllEmpruntByCompteId(Integer id);

    List<EmpruntEntity> getAllEmpruntByOuvrageId(Integer ouvrageId);

    EmpruntEntity addEmprunt(EmpruntEntity emprunt);

    void updateEmprunt(EmpruntEntity emprunt);

    void deleteEmprunt(Integer id);

}
