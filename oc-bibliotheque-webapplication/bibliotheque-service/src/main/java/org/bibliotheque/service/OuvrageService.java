package org.bibliotheque.service;

import org.bibliotheque.repository.OuvrageRepository;
import org.bibliotheque.wsdl.LivreType;
import org.bibliotheque.wsdl.OuvrageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OuvrageService {

    @Autowired
    private OuvrageRepository repository;


    /**
     * ==== CETTE METHODE RECUPERER UN OUVRAGE PAR SON IDENTIFIANT ====
     * @param id
     * @return UN OUVRAGE
     * @see OuvrageRepository#ouvrageById(Integer)
     */
    public OuvrageType ouvrageById(Integer id){
        return repository.ouvrageById(id);
    }


    /**
     * ==== CETTE METHODE RECUPERER TOUS LES OUVRAGES ====
     * @return UNE LISTE D'OUVRAGES
     * @see OuvrageRepository#ouvrageTypeList()
     */
    public List<OuvrageType> ouvrageTypeList(){
        return repository.ouvrageTypeList();
    }


    /**
     * CETTE METHODE TRIE LES LIVRES DISPONIBLES DE OUVRAGE
     * @param livreTypeList
     * @return LA LISTE DES LIVRES DISPONIBLE POUR UN OUVRAGE
     */
    public List<LivreType> nombreDeLivreDispo(List<LivreType> livreTypeList){

        List<LivreType> livreTypeListDispo = new ArrayList<>();

        for (LivreType livreType : livreTypeList){
            if (livreType.getStatut().equals("disponible")){
                livreTypeListDispo.add(livreType);
            }
        }

        return livreTypeListDispo;
    }


    /**
     * CETTE METHODE RÉCUPÉRER TOUS LES OUVRAGES CORRESPONDANT AU GENRE SELECTIONNE
     * @param motCle
     * @return UNE LISTE D'OUVRAGE TRIEE PAR GENRE OU PAR AUTEURS
     */
    public List<OuvrageType> ouvragesByGenreList(String motCle){

        /**@see OuvrageRepository#ouvrageTypeList()*/
        List<OuvrageType> ouvrageTypeList = repository.ouvrageTypeList();
        List<OuvrageType> ouvrageTypeListByGenre = new ArrayList<>();

        for (OuvrageType ouvrageType : ouvrageTypeList){

            if (ouvrageType.getGenre().equals(motCle) || ouvrageType.getAuteur().equals(motCle)){
                ouvrageTypeListByGenre.add(ouvrageType);
            }
        }

        return ouvrageTypeListByGenre;
    }


    /**
     * CETTE METHODE RÉCUPÉRER LES GENRES DES OUVRAGES ET SUPPRIME LES DOUBLONS
     * @param ouvrageTypeList
     * @return UNE LISTE DE GENRES
     */
    public Set<String> ouvrageGenreList(List<OuvrageType> ouvrageTypeList){

        Set<String> genreSetList = new HashSet<>();

        for (OuvrageType ouvrageType : ouvrageTypeList){
            genreSetList.add(ouvrageType.getGenre());
        }

        return genreSetList;
    }


    /**
     * CETTE METHODE RÉCUPÉRER LES AUTEURS DES OUVRAGES ET SUPPRIME LES DOUBLONS
     * @param ouvrageTypeList
     * @return UNE LISTE D'AUTEURS
     */
    public Set<String> ouvrageAuteurList(List<OuvrageType> ouvrageTypeList){

        Set<String> auteurSetList = new HashSet<>();

        for (OuvrageType ouvrageType : ouvrageTypeList){
            auteurSetList.add(ouvrageType.getAuteur());
        }

        return auteurSetList;
    }


    /**
     * CETTE METHODE TRIE LES LIVRES DISPONIBLES DES LISTE D'OUVRAGES
     * @param ouvrageTypes
     * @return LA LISTE DES LIVRES DISPONIBLE POUR CHAQUE OUVRAGE
     */
    public List<OuvrageType> livresDispoForOuvrage(List<OuvrageType> ouvrageTypes){

        for (OuvrageType ouvrageType : ouvrageTypes){

            List<LivreType> livreTypeListDispo = nombreDeLivreDispo(ouvrageType.getLivres());
            ouvrageType.getLivres().clear();
            ouvrageType.getLivres().addAll(livreTypeListDispo);
        }

        return ouvrageTypes;
    }

}
