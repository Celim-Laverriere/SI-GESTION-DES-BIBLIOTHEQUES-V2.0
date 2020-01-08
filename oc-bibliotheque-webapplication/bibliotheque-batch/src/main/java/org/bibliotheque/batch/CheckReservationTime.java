package org.bibliotheque.batch;

import org.bibliotheque.Mail.MessagesMail;
import org.bibliotheque.Mail.SendingMailThroughGmailSMTPServer;
import org.bibliotheque.service.CompteService;
import org.bibliotheque.service.EmpruntService;
import org.bibliotheque.service.OuvrageService;
import org.bibliotheque.service.ReservationService;
import org.bibliotheque.wsdl.*;
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
import java.io.InputStream;
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

    @Autowired
    private CompteService compteService;

    @Autowired
    private MessagesMail messagesMail;

    @Autowired
    private SendingMailThroughGmailSMTPServer sendingMailThroughGmailSMTPServer;

    @Override
    public void beforeStep(StepExecution stepExecution) {

    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }

    /**
     *
     * @param stepContribution
     * @param chunkContext
     * @return
     * @throws Exception
     */
    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

        List<ReservationType> reservationTypeListByFirstPosition = reservationTypeListByFirstPosition();

        Date dateToDay = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Vérifie que le délai de 48 heures pour récupérer le livre n'est pas dépassé
        for (ReservationType reservationType : reservationTypeListByFirstPosition) {

            GregorianCalendar calendar = new GregorianCalendar();

            Date dateEmpruntFin = dateFormat.parse(reservationType.getDateResaDisponible().toString());

            calendar.setTime(dateEmpruntFin);
            calendar.add(GregorianCalendar.DATE, 1);
            XMLGregorianCalendar dateResaLimite = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);

            dateEmpruntFin = dateFormat.parse(dateResaLimite.toString());

            if (dateEmpruntFin.before(dateToDay) && reservationType.getStatut().equals("En cours")){
                System.out.println("La réservation à exéder 48 heures !" + dateEmpruntFin + " Compte : " +
                        reservationType.getCompteId() );
                String statut = reservationBeCancelled(reservationType);

                CompteType compteType = compteService.compteById(reservationType.getCompteId());
                OuvrageType ouvrageType = ouvrageService.ouvrageById(reservationType.getOuvrageId());
                String subject = "Info réservation : Bibliothèque de TILLY";

                String mail = messagesMail.textMailDelaiExpirer(compteType, ouvrageType);
                sendingMailThroughGmailSMTPServer.sendMessage(subject, mail, compteType.getMail(),
                        ouvrageType.getPhotos().get(0).getUrlPhoto());
            }
        }

        // Vérifie la date de retour d'un livre pour envoyer un mail au client qui à réservé le livre pour venir le chercher

        reservationTypeListByFirstPosition.clear();
        reservationTypeListByFirstPosition = reservationTypeListByFirstPosition();

        for (ReservationType reservationType : reservationTypeListByFirstPosition) {

            Date dateEmpruntFin = dateFormat.parse(reservationType.getDateResaDisponible().toString());

            if (dateFormat.format(dateEmpruntFin).equals(dateFormat.format(dateToDay)) &&
                    ouvrageRenderedForReservation(reservationType)) {

                CompteType compteType = compteService.compteById(reservationType.getCompteId());
                OuvrageType ouvrageType = ouvrageService.ouvrageById(reservationType.getOuvrageId());

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dateEmpruntFin);
                calendar.add(Calendar.DATE, 1);
                Date dateOuvrageDisponible = calendar.getTime();

                String subject = "Info réservation : Bibliothèque de TILLY";
                String mail = messagesMail.textMailDisponible(dateOuvrageDisponible, compteType, ouvrageType);

                sendingMailThroughGmailSMTPServer.sendMessage(subject, mail, compteType.getMail(),
                        ouvrageType.getPhotos().get(0).getUrlPhoto());
            }
        }

        return RepeatStatus.FINISHED;
    }


    /**
     * ==== CETTE METHODE VÉRIFIE SI LES DATES DE RESERVATIONS N'EXEDENT PAS 48 HEURES  ====
     * @param
     * @return
     * @throws ParseException
     * @throws DatatypeConfigurationException
     */
    public String reservationBeCancelled(ReservationType reservationType) throws DatatypeConfigurationException {

        GregorianCalendar calendar = new GregorianCalendar();
        Date dateToDay = new Date();

        List<ReservationType> reservationTypeList = reservationService.reservationTypeListByOuvrageId(reservationType.getOuvrageId());

        List<ReservationType> reservationTypesUpdate = new ArrayList<>();

        for (ReservationType reservation : reservationTypeList) {

            if (!reservation.getStatut().equals("Annuler")){
                reservation.setNumPositionResa(reservation.getNumPositionResa() - 1);

                calendar.setTime(dateToDay);
                XMLGregorianCalendar gregorianCalendarToDay = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
                reservation.setDateResaDisponible(gregorianCalendarToDay);

                reservationTypesUpdate.add(reservation);
                reservationService.updateReservation(reservation);
            }
        }

        reservationType.setStatut("Annuler");
        reservationType.setNumPositionResa(0);

        String statut = reservationService.updateReservation(reservationType);

        return statut;
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


    /**
     *
     * @param reservationType
     * @return
     */
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
