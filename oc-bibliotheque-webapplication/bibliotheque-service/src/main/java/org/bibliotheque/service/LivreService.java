package org.bibliotheque.service;

import org.bibliotheque.client.LivreClient;
import org.bibliotheque.repository.LivreRepository;
import org.bibliotheque.wsdl.LivreType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LivreService {

    @Autowired
    private LivreRepository repository;


    /**
     * ==== CETTE METHODE RECUPERER UN LIVRE PAR SON IDENTIFIANT ====
     * @param id
     * @return LivreType
     * @see LivreClient#getLivreById(Integer)
     */
    public LivreType livreById(Integer id){
        return repository.livreById(id);
    }

}
