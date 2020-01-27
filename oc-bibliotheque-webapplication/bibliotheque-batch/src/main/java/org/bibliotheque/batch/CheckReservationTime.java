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
     * CETTE PARTIE VERIFIE QUE LE DELAI DE 48 HEURES POUR RECUPERER LE LIVRE N'EST PAS DEPASSE
     * ET VERIFIE SI LE LIVRE A BIEN ETE RETOURNE, POUR ENVOYER UN MAIL AU CLIENT QUI L’A RESERVE POUR VENIR LE CHERCHER
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

        /* ==== CETTE PARTIE VERIFIE QUE LE DELAI DE 48 HEURES POUR RECUPERER LE LIVRE N'EST PAS DEPASSE ==== */

        for (ReservationType reservationType : reservationTypeListByFirstPosition) {

            GregorianCalendar calendar = new GregorianCalendar();

            Date dateEmpruntFin = dateFormat.parse(reservationType.getDateResaDisponible().toString());

            calendar.setTime(dateEmpruntFin);
            calendar.add(GregorianCalendar.DATE, 1);
            XMLGregorianCalendar dateResaLimite = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);

            dateEmpruntFin = dateFormat.parse(dateResaLimite.toString());

            List<EmpruntType> empruntTypeList = empruntService.getAllEmpruntByOuvrageId(reservationType.getOuvrageId());

            for (EmpruntType empruntType : empruntTypeList) {

                if (dateEmpruntFin.before(dateToDay) && reservationType.getStatut().equals("En cours")
                && empruntType.getStatut().equals("Rendu")){

                    String statut = reservationBeCancelled(reservationType);

                    CompteType compteType = compteService.compteById(reservationType.getCompteId());
                    OuvrageType ouvrageType = ouvrageService.ouvrageById(reservationType.getOuvrageId());
                    String subject = "Info réservation : Bibliothèque de TILLY";

                    String mail = messagesMail.textMailDelaiExpirer(compteType, ouvrageType);
                    sendingMailThroughGmailSMTPServer.sendMessage(subject, mail, compteType.getMail(),
                            ouvrageType.getPhotos().get(0).getUrlPhoto());
                }
            }


        }

        reservationTypeListByFirstPosition.clear();
        reservationTypeListByFirstPosition = reservationTypeListByFirstPosition();

        /* ==== CETTE PARTIE VERIFIE SI LE LIVRE A BIEN ETE RETOURNE, POUR ENVOYER UN MAIL AU CLIENT
        QUI L’A RESERVE POUR VENIR LE CHERCHER ==== */

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
     * CETTE METHODE RECUPERE LA RESERVATION EN PREMIERE POSITION DANS LA LISTE
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
     * CETTE METHODE VERIFIE SI LE LIVRE A BIEN ETE RENDU
     * @param reservationType
     * @return
     */
    public Boolean ouvrageRenderedForReservation(ReservationType reservationType) {

        OuvrageType ouvrageType = ouvrageService.ouvrageById(reservationType.getOuvrageId());

        Boolean livreReserver = false;

        for(LivreType livreType : ouvrageType.getLivres()){

            List<EmpruntType> empruntTypeList = empruntService.getAllEmpruntByOuvrageId(ouvrageType.getId());

            for (EmpruntType empruntType : empruntTypeList) {
                if (livreType.getStatut().equals("Reserver") && empruntType.getLivreId() == livreType.getId()
                && empruntType.getStatut().equals("Rendu")) {
                    livreReserver = true;
                }
            }
        }

        return livreReserver;
    }

}
