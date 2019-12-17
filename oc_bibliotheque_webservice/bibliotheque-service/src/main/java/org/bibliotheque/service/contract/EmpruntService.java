package org.bibliotheque.service.contract;

import org.bibliotheque.entity.EmpruntEntity;
import java.util.List;

public interface EmpruntService {

    EmpruntEntity getEmpruntById(Integer id);

    List<EmpruntEntity> getAllEmprunts();

    List<EmpruntEntity> getAllEmpruntByCompteId(Integer id);

    List<EmpruntEntity> getAllEmpruntByOuvrageId(Integer ouvrageId);

    EmpruntEntity addEmprunt(EmpruntEntity emprunt);

    boolean updateEmprunt(EmpruntEntity emprunt);

    boolean deleteEmprunt(Integer id);

}
