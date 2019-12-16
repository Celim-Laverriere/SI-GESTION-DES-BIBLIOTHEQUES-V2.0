package org.bibliotheque.service.impl;

import lombok.NoArgsConstructor;
import org.bibliotheque.entity.EmpruntEntity;
import org.bibliotheque.repository.EmpruntRepository;
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
        try{
            return this.repository.save(emprunt);
        } catch (Exception pEX){
            pEX.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean updateEmprunt(EmpruntEntity emprunt) {
        try{
            this.repository.save(emprunt);
            return true;
        } catch (Exception pEX){
            pEX.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteEmprunt(Integer id) {
        try{
            this.repository.deleteById(id);
            return true;
        }catch (Exception pEX){
            pEX.printStackTrace();
            return false;
        }

    }

    @Override
    public List<EmpruntEntity> getAllEmpruntByCompteId(Integer id) {
        List<EmpruntEntity> empruntEntityList = new ArrayList<>();
        this.repository.findAllByCompteId(id).forEach(e -> empruntEntityList.add(e));
        return empruntEntityList;
    }

}
