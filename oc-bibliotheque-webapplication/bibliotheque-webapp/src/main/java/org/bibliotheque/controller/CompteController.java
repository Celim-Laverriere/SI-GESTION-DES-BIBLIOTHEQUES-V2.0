package org.bibliotheque.controller;

import org.bibliotheque.service.CompteService;
import org.bibliotheque.wsdl.CompteType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.validation.Valid;

@Controller
public class CompteController {

    @Autowired
    private CompteService compteService;


    @Secured(value = "ROLE_USER")
    @RequestMapping(value = "/compte", method = RequestMethod.GET)
    public String compte(){
        return "/compte/compte";
    }


    @Secured(value = "ROLE_USER")
    @RequestMapping(value = "/infoPerso", method = RequestMethod.GET)
    public String formUpCompte(CompteType compteType, @RequestParam(name = "compteId") Integer compteId, Model model){

        /**@see CompteService#compteById(Integer)*/
        compteType = compteService.compteById(compteId);

        model.addAttribute("compteType", compteType);

        return "compte/infoPerso";
    }


    @Secured(value = "ROLE_USER")
    @RequestMapping(value = "/upCompte", method = RequestMethod.POST)
    public String uuCompte(@Valid CompteType compteType, BindingResult bindingResult,
                           RedirectAttributes redirectAttributes){

        /**@see CompteService#upCompte(CompteType)*/
        String message = compteService.upCompte(compteType);

        return "compte/infoPerso";
    }

}
