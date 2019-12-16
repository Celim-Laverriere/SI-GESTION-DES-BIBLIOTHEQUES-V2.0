package org.bibliotheque.controller;

import org.bibliotheque.security.entity.Users;
import org.bibliotheque.service.EmpruntService;
import org.bibliotheque.wsdl.EmpruntType;
import org.bibliotheque.wsdl.LivreType;
import org.bibliotheque.wsdl.OuvrageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpSession;
import javax.xml.datatype.DatatypeConfigurationException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
public class EmpruntController {

    @Autowired
    private EmpruntService empruntService;


    @Secured(value = "ROLE_USER")
    @RequestMapping(value = "/emprunt", method = RequestMethod.GET)
    public String getAllEmpruntByCompteId(HttpSession session, Model model, @RequestParam(name = "statutCode",
                                            required = false) String statutCode){

        Users user = (Users) session.getAttribute("user");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        model.addAttribute("dateFormat", dateFormat);

        /**@see EmpruntService#getAllEmpruntByCompteId(Integer)*/
        List<EmpruntType> empruntTypeList = empruntService.getAllEmpruntByCompteId(user.getUserId());

        /**@see EmpruntService#livreTypeListEmprunter(List)*/
        List<LivreType> livreTypeList = empruntService.livreTypeListEmprunter(empruntTypeList);

        /**@see EmpruntService#ouvrageTypeListEmprunter(List)*/
        List<OuvrageType> ouvrageTypeList = empruntService.ouvrageTypeListEmprunter(livreTypeList);

        if (statutCode != null){
            model.addAttribute("statutCode", statutCode);
        }

        model.addAttribute("empruntTypeList", empruntTypeList);
        model.addAttribute("livreTypeList", livreTypeList);
        model.addAttribute("ouvrageTypeList", ouvrageTypeList);

        return "compte/emprunt";
    }


    @Secured(value = "ROLE_USER")
    @RequestMapping(value = "/prolongation", method = RequestMethod.GET)
    public String upEmpruntProlongation(@RequestParam(name = "empruntId")Integer empruntId,
                                            HttpSession session) throws DatatypeConfigurationException, ParseException {

        /**@see EmpruntService#upEmpruntProlongation(Integer)*/
        String statusCode = empruntService.upEmpruntProlongation(empruntId);

        return "redirect:/emprunt?statutCode=" + statusCode;
    }
}
