package org.bibliotheque.controller;

import org.bibliotheque.service.OuvrageService;
import org.bibliotheque.wsdl.OuvrageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;

@Controller
public class AccueilController {

    @Autowired
    private OuvrageService ouvrageService;


    @RequestMapping(value = "/")
    public String accueil(HttpSession session){

        /**@see OuvrageService#ouvrageTypeList()*/
        List<OuvrageType> ouvrageTypeList = ouvrageService.ouvrageTypeList();

        /**@see OuvrageService#ouvrageGenreList(List)*/
        Set<String> genresSet = ouvrageService.ouvrageGenreList(ouvrageTypeList);

        /**@see OuvrageService#ouvrageAuteurList(List)*/
        Set<String> auteursSet = ouvrageService.ouvrageAuteurList(ouvrageTypeList);

        session.setAttribute("genreList", genresSet);
        session.setAttribute("auteurList", auteursSet);

        return "accueil";
    }
}
