package org.bibliotheque.service.contract;

import org.bibliotheque.entity.LivreEntity;
import java.util.List;

public interface LivreService {

   LivreEntity getLivreById(Integer id);

   List<LivreEntity> getAllLivres();

   LivreEntity addLivre(LivreEntity livre);

   boolean updateLivre(LivreEntity livre);

   boolean deleteLivre(Integer id);

}
