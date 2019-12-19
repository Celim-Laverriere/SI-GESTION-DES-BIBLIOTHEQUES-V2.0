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

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

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


        Date dateToDay = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

         List<ReservationType> reservationTypeList = reservationService.getAllReservation();

        for (ReservationType reservationType : reservationTypeList) {

            GregorianCalendar calendar = new GregorianCalendar();

            Date dateResa = dateFormat.parse(reservationType.getDateDemandeDeResa().toString());
            calendar.setTime(dateResa);
            calendar.add(GregorianCalendar.DATE, 2);
            XMLGregorianCalendar dateResaLimite = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);

            dateResa = dateFormat.parse(dateResaLimite.toString());

            if (dateToDay.before(dateResa)){
                System.out.println("before : " + dateFormat.format(dateResa));
            }

            if (dateToDay.after(dateResa)){
                System.out.println("After : " + dateFormat.format(dateResa));
            }
        }



        return null;
    }
}
