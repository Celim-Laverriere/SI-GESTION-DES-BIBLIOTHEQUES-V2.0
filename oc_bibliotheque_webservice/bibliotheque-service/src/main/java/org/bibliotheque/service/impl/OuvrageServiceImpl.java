package org.bibliotheque.service.impl;

import lombok.NoArgsConstructor;
import org.bibliotheque.entity.OuvrageEntity;
import org.bibliotheque.repository.OuvrageRepository;
import org.bibliotheque.service.contract.OuvrageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@NoArgsConstructor
public class OuvrageServiceImpl implements OuvrageService {

    @Autowired
    private OuvrageRepository ouvrageRepository;


    @Override
    public OuvrageEntity getOuvrageById(Integer id) {
        return this.ouvrageRepository.findById(id).get();
    }

    @Override
    public List<OuvrageEntity> getAllOuvrages() {
        List<OuvrageEntity> ouvrageEntities = new ArrayList<>();
        this.ouvrageRepository.findAll().forEach(e -> ouvrageEntities.add(e));
        return ouvrageEntities;
    }

    @Override
    public OuvrageEntity addOuvrage(OuvrageEntity ouvrage) {
        try{
            return this.ouvrageRepository.save(ouvrage);
        } catch (Exception pEX){
            pEX.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean updateOuvrage(OuvrageEntity ouvrage) {
        try {
            this.ouvrageRepository.save(ouvrage);
            return true;
        } catch (Exception pEX){
            pEX.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteOuvrage(Integer id) {
        try {
            this.ouvrageRepository.deleteById(id);
            return true;
        } catch (Exception pEX){
            pEX.printStackTrace();
            return false;
        }
    }

    @Override
    public List<OuvrageEntity> getAllOuvagesByKeyword(String keyword) {
        List<OuvrageEntity> ouvrageEntities = new ArrayList<>();
        this.ouvrageRepository.findAllOuvragesByKeyword(keyword).forEach(e -> ouvrageEntities.add(e));
        return ouvrageEntities;
    }

}
