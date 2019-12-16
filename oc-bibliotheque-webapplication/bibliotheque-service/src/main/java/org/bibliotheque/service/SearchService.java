package org.bibliotheque.service;

import org.bibliotheque.repository.SearchRepository;
import org.bibliotheque.wsdl.OuvrageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SearchService {

    @Autowired
    private SearchRepository searchRepository;


    /**
     * ==== CETTE METHODE RECUPERER UN OU UNE LISTE D'OUVRAGE(S) PAR MOT-CLE ====
     * @param keyword
     * @return UNE LISTE D'OUVRAGES
     */
    public List<OuvrageType> ouvrageTypeListByKeyword(String keyword){
        return searchRepository.ouvrageTypeListByKeyword(keyword);
    }
}
