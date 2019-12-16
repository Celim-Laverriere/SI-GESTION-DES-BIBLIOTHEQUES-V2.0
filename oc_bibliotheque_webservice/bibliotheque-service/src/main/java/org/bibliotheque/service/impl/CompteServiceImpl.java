package org.bibliotheque.service.impl;

import lombok.NoArgsConstructor;
import org.bibliotheque.entity.CompteEntity;
import org.bibliotheque.repository.CompteRepository;
import org.bibliotheque.service.contract.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@NoArgsConstructor
public class CompteServiceImpl implements CompteService {

    @Autowired
    private CompteRepository repository;


    @Override
    public CompteEntity getCompteById(Integer id) {
        return this.repository.findById(id).get();
    }

    @Override
    public List<CompteEntity> getAllComptes() {
        List<CompteEntity> compteEntities = new ArrayList<>();
        this.repository.findAll().forEach(e -> compteEntities.add(e));
        return compteEntities;
    }

    @Override
    public CompteEntity addCompte(CompteEntity compte) {
        try{
            return this.repository.save(compte);
        } catch (Exception pEX){
            pEX.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean updateCompte(CompteEntity compte) {
        try{
            this.repository.save(compte);
            return true;
        } catch (Exception pEX){
            pEX.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteCompte(Integer id) {
        try{
            this.repository.deleteById(id);
            return true;
        } catch (Exception pEX){
            pEX.printStackTrace();
            return false;
        }
    }

}
