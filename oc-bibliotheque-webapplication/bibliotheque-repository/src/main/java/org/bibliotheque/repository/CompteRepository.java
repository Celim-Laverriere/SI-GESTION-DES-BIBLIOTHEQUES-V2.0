package org.bibliotheque.repository;

import org.bibliotheque.client.CompteClient;
import org.bibliotheque.wsdl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CompteRepository {

    @Autowired
    private CompteClient client;


    /**
     * ==== CETTE METHODE RECUPERER UN COMPTE CLIENT PAR SON IDENTIFIANT ====
     * @param id
     * @return LES INFORMATIONS D'UN COMPTE
     * @see CompteClient#getCompteById(Integer)
     */
    public CompteType compteById(Integer id){
        GetCompteByIdResponse response = client.getCompteById(new Integer(id));
        return response.getCompteType();
    }


    /**
     * ==== CETTE METHODE MET A JOUR LE COMPTE UTILISATEUR ====
     * @param compteType
     * @return UN MESSAGE DE CONFIRMATION
     * @see CompteClient#updateCompte(CompteType)
     */
    public String upCompte(CompteType compteType){
        UpdateCompteResponse response = client.updateCompte(compteType);
        return response.getServiceStatus().getStatusCode();
    }


}
