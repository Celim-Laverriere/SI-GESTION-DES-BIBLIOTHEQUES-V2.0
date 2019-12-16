package org.bibliotheque.controller;

import org.bibliotheque.service.OuvrageService;
import org.bibliotheque.service.SearchService;
import org.bibliotheque.wsdl.OuvrageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    @Autowired
    private OuvrageService ouvrageService;


    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String searchOuvrageByKeyword(Model model, @RequestParam(value = "keyword")String keyword){

        /**@see SearchService#ouvrageTypeListByKeyword(String)*/
        List<OuvrageType> ouvrageTypeList = searchService.ouvrageTypeListByKeyword(keyword);

        /**@see OuvrageService#livresDispoForOuvrage(List)*/
        ouvrageTypeList = ouvrageService.livresDispoForOuvrage(ouvrageTypeList);

        model.addAttribute("ouvrageList", ouvrageTypeList);

        return "ouvrage/ouvrageList";
    }
}
