package org.bibliotheque.controller;

import org.bibliotheque.service.OuvrageService;
import org.bibliotheque.wsdl.LivreType;
import org.bibliotheque.wsdl.OuvrageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
public class OuvrageController {

    private static final Logger logger = LoggerFactory.getLogger(OuvrageController.class);

    @Autowired
    private OuvrageService ouvrageService;


    @RequestMapping(value = "/ouvrages", method = RequestMethod.GET)
    public String ouvrages(final Model model){

        /**@see OuvrageService#ouvrageTypeList()*/
        List<OuvrageType> ouvrageTypeList = ouvrageService.ouvrageTypeList();

        /**@see OuvrageService#livresDispoForOuvrage(List)*/
        ouvrageTypeList = ouvrageService.livresDispoForOuvrage(ouvrageTypeList);

        model.addAttribute("ouvrageList", ouvrageTypeList);

            return "ouvrage/ouvrageList";
    }


    @RequestMapping(value = "/ouvrage", method = RequestMethod.GET)
    public String ouvrageDetail(Model model, @RequestParam(name = "ouvrageId") Integer ouvrageId){

        String ouvrageReturn;

        try{
            /**@see OuvrageService#ouvrageById(Integer)*/
            OuvrageType ouvrageType = ouvrageService.ouvrageById(ouvrageId);

            /**@see OuvrageService#nombreDeLivreDispo(List)*/
            List<LivreType> livreTypeListDispo = ouvrageService.nombreDeLivreDispo(ouvrageType.getLivres());

            model.addAttribute("livreTypeListDispo", livreTypeListDispo);
            model.addAttribute("ouvrageDetail", ouvrageType);

            ouvrageReturn = "ouvrage/ouvrageDetail";

        } catch (NullPointerException pEX){
            logger.error("/ouvrage : {}", pEX.toString());
            ouvrageReturn = "error";
        }
        return ouvrageReturn;
    }


    @RequestMapping(value = "/ouvragesByGenre", method = RequestMethod.GET)
    public String ouvragesByGenre(Model model, @RequestParam(name = "motCle") String motCle){

        /**@see OuvrageService#ouvragesByGenreList(String)*/
        List<OuvrageType> ouvrageTypeList = ouvrageService.ouvragesByGenreList(motCle);

        /**@see OuvrageService#livresDispoForOuvrage(List)*/
        ouvrageTypeList = ouvrageService.livresDispoForOuvrage(ouvrageTypeList);

        model.addAttribute("ouvrageList", ouvrageTypeList);

        return "ouvrage/ouvrageList";
    }

}
