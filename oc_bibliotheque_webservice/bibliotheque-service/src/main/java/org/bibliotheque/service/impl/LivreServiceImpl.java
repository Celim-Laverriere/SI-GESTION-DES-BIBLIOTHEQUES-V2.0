package org.bibliotheque.service.impl;

import lombok.NoArgsConstructor;
import org.bibliotheque.entity.LivreEntity;
import org.bibliotheque.repository.LivreRepository;
import org.bibliotheque.service.contract.LivreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@NoArgsConstructor
public class LivreServiceImpl implements LivreService {

    @Autowired
    private LivreRepository repository;


    @Override
    public LivreEntity getLivreById(Integer id) {
        return this.repository.findById(id).get();
    }

    @Override
    public List<LivreEntity> getAllLivres() {
        List<LivreEntity> livreEntities = new ArrayList<LivreEntity>();
        this.repository.findAll().forEach(e -> livreEntities.add(e));
        return livreEntities;
    }

    @Override
    public LivreEntity addLivre(LivreEntity livre) {
        try{
            return this.repository.save(livre);
        } catch (Exception pEX){
            pEX.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean updateLivre(LivreEntity livre) {
        try{
            this.repository.save(livre);
            return true;
        } catch (Exception pEX){
            pEX.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteLivre(Integer id) {
        try {
            this.repository.deleteById(id);
            return true;
        } catch (Exception pEX){
            pEX.printStackTrace();
            return false;
        }
    }

}
