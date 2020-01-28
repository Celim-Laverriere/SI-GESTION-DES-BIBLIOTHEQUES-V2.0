package org.bibliotheque.service.impl;

import lombok.NoArgsConstructor;
import org.bibliotheque.entity.EmpruntEntity;
import org.bibliotheque.entity.LivreEntity;
import org.bibliotheque.repository.EmpruntRepository;
import org.bibliotheque.repository.LivreRepository;
import org.bibliotheque.service.contract.EmpruntService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@NoArgsConstructor
public class EmpruntServiceImpl implements EmpruntService {

    @Autowired
    private EmpruntRepository repository;

    @Autowired
    private LivreRepository livreRepository;


    @Override
    public EmpruntEntity getEmpruntById(Integer id) {
        return this.repository.findById(id).get();
    }

    @Override
    public List<EmpruntEntity> getAllEmprunts() {
        List<EmpruntEntity> empruntEntities = new ArrayList<>();
        this.repository.findAll().forEach(e -> empruntEntities.add(e));
        return empruntEntities;
    }

    @Override
    public EmpruntEntity addEmprunt(EmpruntEntity emprunt) {
        return this.repository.save(emprunt);
    }

    @Override
    public void updateEmprunt(EmpruntEntity emprunt) {
        this.repository.save(emprunt);
    }

    @Override
    public void deleteEmprunt(Integer id) {
        this.repository.deleteById(id);
    }

    @Override
    public List<EmpruntEntity> getAllEmpruntByCompteId(Integer id) {
        List<EmpruntEntity> empruntEntityList = new ArrayList<>();
        this.repository.findAllByCompteId(id).forEach(e -> empruntEntityList.add(e));
        return empruntEntityList;
    }

    @Override
    public List<EmpruntEntity> getAllEmpruntByOuvrageId(Integer ouvrageId) {

        List<LivreEntity> livreEntityList = new ArrayList<>();
        List<EmpruntEntity> empruntEntityListByOuvrageId = new ArrayList<>();

        this.livreRepository.findAllLivreByOuvrageId(ouvrageId).forEach(e -> livreEntityList.add(e));

        for (LivreEntity livreEntity : livreEntityList) {
            this.repository.findAllEmpruntByOuvrageId(livreEntity.getId()).forEach(e -> empruntEntityListByOuvrageId.add(e));
        }

        return empruntEntityListByOuvrageId;
    }



}
