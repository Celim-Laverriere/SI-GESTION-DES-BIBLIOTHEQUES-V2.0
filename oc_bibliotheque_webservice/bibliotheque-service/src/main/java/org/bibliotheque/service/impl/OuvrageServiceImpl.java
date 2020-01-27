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
        return this.ouvrageRepository.save(ouvrage);
    }

    @Override
    public void updateOuvrage(OuvrageEntity ouvrage) {
        this.ouvrageRepository.save(ouvrage);
    }

    @Override
    public void deleteOuvrage(Integer id) {
        this.ouvrageRepository.deleteById(id);
    }

    @Override
    public List<OuvrageEntity> getAllOuvagesByKeyword(String keyword) {
        List<OuvrageEntity> ouvrageEntities = new ArrayList<>();
        this.ouvrageRepository.findAllOuvragesByKeyword(keyword).forEach(e -> ouvrageEntities.add(e));
        return ouvrageEntities;
    }

}
