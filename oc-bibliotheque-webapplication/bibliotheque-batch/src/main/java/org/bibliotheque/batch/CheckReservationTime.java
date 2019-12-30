package org.bibliotheque.batch;

import org.bibliotheque.service.ReservationService;
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


    @Override
    public void beforeStep(StepExecution stepExecution) {

    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {



        List<ReservationType> reservationTypeList = reservationService.getAllReservation();
        List<ReservationType> reservationTypeListBeCancelled = reservationListBeCancelled(reservationTypeList);
        List<ReservationType> reservationTypeListUpdate = new ArrayList<>();

        for (ReservationType reservationType : reservationTypeListBeCancelled) {

            for (ReservationType type : reservationTypeList) {

                if (reservationType.getNumPositionResa() < type.getNumPositionResa() && type.getId() != reservationType.getId() && type.getNumPositionResa() > 0) {
                    type.setNumPositionResa(type.getNumPositionResa() - 1);
                    System.out.println(type.getNumPositionResa());
                    reservationTypeListUpdate.add(type);
                }
            }

            reservationTypeList.clear();
            reservationTypeList.addAll(reservationTypeListUpdate);
            reservationTypeListUpdate.clear();

        }


        for (ReservationType reservationType : reservationTypeListBeCancelled) {
            reservationType.setNumPositionResa(0);
            reservationTypeList.add(reservationType);
        }

        for (ReservationType reservationType : reservationTypeList){
            String statut = reservationService.updateReservation(reservationType);
        }

        return null;
    }

    /**
     * ==== CETTE METHODE VÉRIFIE LES DATES DE RESERVATIONS ====
     * @param reservationTypeList
     * @return
     * @throws ParseException
     * @throws DatatypeConfigurationException
     */
    public List<ReservationType> reservationListBeCancelled(List<ReservationType> reservationTypeList) throws ParseException, DatatypeConfigurationException {

        Date dateToDay = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        List<ReservationType> reservationTypeListBeCancelled = new ArrayList<>();

        for (ReservationType reservationType : reservationTypeList) {

            GregorianCalendar calendar = new GregorianCalendar();

            Date dateResa = dateFormat.parse(reservationType.getDateDemandeDeResa().toString());
            calendar.setTime(dateResa);
            calendar.add(GregorianCalendar.DATE, 2);
            XMLGregorianCalendar dateResaLimite = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);

            dateResa = dateFormat.parse(dateResaLimite.toString());

            if (dateResa.before(dateToDay) && reservationType.getStatut().equals("En cours")){
                reservationType.setStatut("Annuler");
                reservationTypeListBeCancelled.add(reservationType);
            }
        }

        return reservationTypeListBeCancelled;
    }
}
