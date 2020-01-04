package org.bibliotheque.batch;

import org.bibliotheque.service.EmpruntService;
import org.bibliotheque.service.OuvrageService;
import org.bibliotheque.service.ReservationService;
import org.bibliotheque.wsdl.EmpruntType;
import org.bibliotheque.wsdl.LivreType;
import org.bibliotheque.wsdl.OuvrageType;
import org.bibliotheque.wsdl.ReservationType;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class CheckReservationTime implements Tasklet, StepExecutionListener {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private EmpruntService empruntService;

    @Autowired
    private OuvrageService ouvrageService;

    @Override
    public void beforeStep(StepExecution stepExecution) {

    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

        List<ReservationType> reservationTypeListByFirstPosition = reservationTypeListByFirstPosition();

        Date dateToDay = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Map<Integer, EmpruntType> firtReturnDateEmprunt = earliestReturnDateFinEmprunt(reservationTypeListByFirstPosition);


        // Vérifie la date de retour d'un livre pour envoyer un mail au client qui à réservé le livre pour venir le chercher
        for (ReservationType reservationType : reservationTypeListByFirstPosition) {

            Date dateEmpruntFin = dateFormat.parse(reservationType.getDateOuvrageDisponible().toString());

            if (dateFormat.format(dateEmpruntFin).equals(dateFormat.format(dateToDay)) && ouvrageRenderedForReservation(reservationType)) {
                System.out.println("Mail pour prévenir du retour du livre ! " + "Compte : " + reservationType.getCompteId() +
                        " Ouvrage : " + reservationType.getOuvrageId());

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dateFormat.parse(reservationType.getDateOuvrageDisponible().toString()));
                calendar.add(Calendar.DATE, 1);
                Date dateOuvrageDisponible = calendar.getTime();

                System.out.println(dateFormat.format(dateOuvrageDisponible));
            }

            // A faire (Si le livre n'a pas été rendu)
        }

        //Vérifi que le délai de 48 heures pour récupérer le livre est respécter
        for (ReservationType reservationType : reservationTypeListByFirstPosition) {

            GregorianCalendar calendar = new GregorianCalendar();

            Date dateEmpruntFin = dateFormat.parse(reservationType.getDateOuvrageDisponible().toString());

            calendar.setTime(dateEmpruntFin);
            calendar.add(GregorianCalendar.DATE, 3);
            XMLGregorianCalendar dateResaLimite = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);

            dateEmpruntFin = dateFormat.parse(dateResaLimite.toString());

            if (dateEmpruntFin.before(dateToDay) && reservationType.getStatut().equals("En cours")){
                System.out.println("La réservation à exéder 48 heures !" + dateEmpruntFin + " Compte : " + reservationType.getCompteId() );
                String statut = reservationBeCancelled(reservationType);
            }
        }


        return null;
    }

    /**
     * ==== CETTE METHODE VÉRIFIE SI LES DATES DE RESERVATIONS N'EXEDENT PAS 48 HEURES  ====
     * @param
     * @return
     * @throws ParseException
     * @throws DatatypeConfigurationException
     */
    public String reservationBeCancelled(ReservationType reservationType) throws DatatypeConfigurationException {

        Date dateToDay = new Date();
        GregorianCalendar calendar = new GregorianCalendar();

        List<ReservationType> reservationTypeList = reservationService.reservationTypeListByOuvrageId(reservationType.getOuvrageId());

        List<ReservationType> reservationTypesUpdate = new ArrayList<>();

        for (ReservationType reservation : reservationTypeList) {

            calendar.setTime(dateToDay);
            XMLGregorianCalendar dateOuvrageDisponible = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);

            reservation.setNumPositionResa(reservation.getNumPositionResa() - 1);
            reservation.setDateOuvrageDisponible(dateOuvrageDisponible);
            reservationTypesUpdate.add(reservation);
            reservationService.updateReservation(reservation);

        }

        reservationType.setStatut("Annuler");
        reservationType.setNumPositionResa(0);

        String statut = reservationService.updateReservation(reservationType);

        return statut;
    }

    /**
     *
     * @param reservationTypeList
     * @return
     * @throws ParseException
     */
    public Map<Integer, EmpruntType> earliestReturnDateFinEmprunt(List<ReservationType> reservationTypeList) throws ParseException {

        Map<Integer, Long> jourRestantEmprunt = new HashMap<>();
        Map<Integer, EmpruntType> firstReturnDateByOuvrage = new HashMap<>();


        for (ReservationType reservationType : reservationTypeList) {

            List<EmpruntType> empruntTypeList = empruntService.getAllEmpruntByOuvrageId(reservationType.getOuvrageId());
            List<Long> jourRestantEmpruntList = empruntService.remainingDayOfTheLoan(empruntTypeList);

            jourRestantEmprunt.put(reservationType.getOuvrageId(), jourRestantEmpruntList.get(0));

            empruntTypeList = empruntService.earliestReturnDateForLoan(empruntTypeList, jourRestantEmpruntList);
            firstReturnDateByOuvrage.put(reservationType.getOuvrageId(), empruntTypeList.get(0));

        }

        return firstReturnDateByOuvrage;
    }


    /**
     *
     * @return
     */
    public List<ReservationType> reservationTypeListByFirstPosition(){

        List<ReservationType> reservationTypeList = reservationService.getAllReservation();

        List<ReservationType> reservationTypeListByFirstPosition = new ArrayList<>();

        for (ReservationType reservationType : reservationTypeList) {
            if (reservationType.getNumPositionResa() == 1) {
                reservationTypeListByFirstPosition.add(reservationType);
            }
        }
        return reservationTypeListByFirstPosition;
    }

    public Boolean ouvrageRenderedForReservation(ReservationType reservationType) {

        OuvrageType ouvrageType = ouvrageService.ouvrageById(reservationType.getOuvrageId());
        Boolean livreReserver = false;
        for(LivreType livreType : ouvrageType.getLivres()){

            if (livreType.getStatut().equals("Reserver")) {
                livreReserver = true;
            }
        }

        return livreReserver;
    }

}
