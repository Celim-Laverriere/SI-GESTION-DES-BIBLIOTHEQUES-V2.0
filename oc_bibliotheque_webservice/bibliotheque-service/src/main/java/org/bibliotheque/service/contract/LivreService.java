package org.bibliotheque.service.contract;

import org.bibliotheque.entity.LivreEntity;
import java.util.List;

public interface LivreService {

   LivreEntity getLivreById(Integer id);

   List<LivreEntity> getAllLivres();

   LivreEntity addLivre(LivreEntity livre);

   void updateLivre(LivreEntity livre);

   void deleteLivre(Integer id);

}
