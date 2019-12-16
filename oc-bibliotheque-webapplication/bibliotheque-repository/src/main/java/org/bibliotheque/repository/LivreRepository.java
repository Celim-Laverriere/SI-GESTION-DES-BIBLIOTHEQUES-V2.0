package org.bibliotheque.repository;

import org.bibliotheque.client.LivreClient;
import org.bibliotheque.wsdl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LivreRepository {

    @Autowired
    private LivreClient client;


    /**
     * ==== CETTE METHODE RECUPERER UN LIVRE PAR SON IDENTIFIANT ====
     * @param id
     * @return LivreType
     * @see LivreClient#getLivreById(Integer)
     */
    public LivreType livreById(Integer id) {
        GetLivreByIdResponse response = client.getLivreById(id);
        return response.getLivreType();
    }

}
