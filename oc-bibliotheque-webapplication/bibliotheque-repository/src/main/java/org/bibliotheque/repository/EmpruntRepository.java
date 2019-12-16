package org.bibliotheque.repository;

import org.bibliotheque.client.EmpruntClient;
import org.bibliotheque.wsdl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmpruntRepository {

    @Autowired
    private EmpruntClient empruntClient;


    /**
     * ==== CETTE METHODE RECUPERER TOUS LES EMPRUNTS ====
     * @return LA LISTE DES EMPRUNTS
     */
    public List<EmpruntType> getAllEmprunt(){
        GetAllEmpruntResponse response = empruntClient.getAllEmprunt();
        return response.getEmpruntType();
    }


    /**
     * ==== CETTE METHODE RECUPERER TOUS LES EMPRUNTS D'UN CLIENT ====
     * @param id
     * @return UNE LISTE DES EMPRUNTS D'UN COMPTE
     * @see EmpruntClient#getAllEmpruntByCompteId(Integer)
     */
    public List<EmpruntType> getAllEmpruntByCompteId(Integer id){
        GetAllEmpruntByCompteIdResponse response = empruntClient.getAllEmpruntByCompteId(id);
        return response.getEmpruntType();
    }


    /**
     * ==== CETTE METHODE RECUPERER UN EMPRUNT PAR SON IDENTIFIANT ====
     * @param id
     * @return UN EMPRUNT
     * @see EmpruntClient#getEmpruntById(Integer)
     */
    public EmpruntType getEmpruntById(Integer id){
        GetEmpruntByIdResponse response = empruntClient.getEmpruntById(id);
        return response.getEmpruntType();
    }


    /**
     * ==== CETTE METHODE MET A JOUR UN EMPRUNT POUR PROLONGER SA DUREE ====
     * @param empruntType
     * @return UN STATUT-CODE DE CONFIRMATION
     * @see EmpruntClient#updateEmprunt(EmpruntType)
     */
    public String upEmpruntType(EmpruntType empruntType){
        UpdateEmpruntResponse response = empruntClient.updateEmprunt(empruntType);
        return response.getServiceStatus().getStatusCode();
    }
}
