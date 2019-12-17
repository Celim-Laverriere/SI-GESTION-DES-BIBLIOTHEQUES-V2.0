package org.bibliotheque.controller;

import org.bibliotheque.security.entity.Users;
import org.bibliotheque.service.EmpruntService;
import org.bibliotheque.service.OuvrageService;
import org.bibliotheque.service.ReservationService;
import org.bibliotheque.wsdl.EmpruntType;
import org.bibliotheque.wsdl.OuvrageType;
import org.bibliotheque.wsdl.ReservationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private OuvrageService ouvrageService;

    @Autowired
    private EmpruntService empruntService;

    @GetMapping (value = "/templates/reservation")
    public String reservation(HttpSession session, Model model, @RequestParam(name = "ouvrageId") Integer ouvrageId) throws ParseException {

        Users user = (Users) session.getAttribute("user");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        model.addAttribute("dateFormat", dateFormat);

        OuvrageType ouvrageType = ouvrageService.ouvrageById(ouvrageId);

        List<EmpruntType> empruntTypeList = empruntService.getAllEmpruntByOuvrageId(ouvrageId);

        List<Long> jourRestantEmprunt = empruntService.remainingDayOfTheLoan(empruntTypeList);

        empruntTypeList = empruntService.earliestReturnDateForLoan(empruntTypeList, jourRestantEmprunt);

        List<ReservationType> reservationTypeList = reservationService.reservationTypeListByOuvrageId(ouvrageId);

        Boolean resaDejaEnCours = new Boolean(true);
        Boolean dejaEmprunter = new Boolean(true);

        for (ReservationType reservationType : reservationTypeList) {
            if (reservationType.getCompteId() == user.getUserId()) {
                resaDejaEnCours = false;
            }
        }

        for (EmpruntType empruntType : empruntTypeList) {
            if (empruntType.getCompteId() == user.getUserId()) {
                dejaEmprunter = false;
            }
        }

        model.addAttribute("ouvrage", ouvrageType);
        model.addAttribute("reservationList", reservationTypeList);
        model.addAttribute("dateRetour", empruntTypeList.get(0).getDateFin());
        model.addAttribute("compteurJour", jourRestantEmprunt.get(0));
        model.addAttribute("resaDejaEnCours", resaDejaEnCours);
        model.addAttribute("dejaEmprunter", dejaEmprunter);

        return "reservation/reservation";
    }


    @GetMapping(value = "/mesReservations")
    public String mesReservations(HttpSession session, Model model) throws ParseException {

        Users user = (Users) session.getAttribute("user");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        model.addAttribute("dateFormat", dateFormat);

        List<ReservationType> reservationTypeList = reservationService.reservationTypeListByCompteId(user.getUserId());

        Map<Integer, Long> jourRestantEmprunt = new HashMap<>();
        Map<Integer, EmpruntType> dateRetour = new HashMap<>();

        for (ReservationType reservationType : reservationTypeList) {
            List<EmpruntType> empruntTypeList = empruntService.getAllEmpruntByOuvrageId(reservationType.getOuvrageId());
            List<Long> jourRestantEmpruntList = empruntService.remainingDayOfTheLoan(empruntTypeList);

            jourRestantEmprunt.put(reservationType.getOuvrageId(), jourRestantEmpruntList.get(0));

            empruntTypeList = empruntService.earliestReturnDateForLoan(empruntTypeList, jourRestantEmpruntList);
            dateRetour.put(reservationType.getOuvrageId(), empruntTypeList.get(0));
        }

        List<OuvrageType> ouvrageTypeList = ouvrageService.ouvrageTypeList();

        model.addAttribute("dateRetour", dateRetour);
        model.addAttribute("jourRestantEmprunt", jourRestantEmprunt);
        model.addAttribute("reservationTypeList", reservationTypeList);
        model.addAttribute("ouvrageTypeList", ouvrageTypeList);

        return "compte/resaCompte";
    }

    @GetMapping(value = "detailReservation")
    public String deailReservation(Model model, @RequestParam(name = "ouvrageId") Integer ouvrageId) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        model.addAttribute("dateFormat", dateFormat);

        OuvrageType ouvrageType = ouvrageService.ouvrageById(ouvrageId);

        model.addAttribute("ouvrageType", ouvrageType);

        System.out.println(ouvrageId);

        return "compte/resaDetailCompte";
    }

    @GetMapping(value = "addReservation")
    public String addReservation(Model model, @RequestParam(name = "ouvrageId") Integer ouvrageId) {
        System.out.println(ouvrageId);

        return null;

    }


}
