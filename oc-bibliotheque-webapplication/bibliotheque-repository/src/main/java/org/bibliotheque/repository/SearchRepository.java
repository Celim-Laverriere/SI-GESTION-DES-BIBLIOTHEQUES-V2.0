package org.bibliotheque.repository;

import org.bibliotheque.client.SearchClient;
import org.bibliotheque.wsdl.GetSearchByKeywordResponse;
import org.bibliotheque.wsdl.OuvrageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class SearchRepository {

    @Autowired
    private SearchClient searchClient;


    /**
     * ==== CETTE METHODE RECUPERER UN OU UNE LISTE D'OUVRAGE(S) PAR MOT-CLE ====
     * @param keyword
     * @return UNE LISTE D'OUVRAGES
     */
    public List<OuvrageType> ouvrageTypeListByKeyword(String keyword){
        GetSearchByKeywordResponse response = searchClient.getSearchByKeyword(keyword);
        return response.getOuvrageType();
    }
}
