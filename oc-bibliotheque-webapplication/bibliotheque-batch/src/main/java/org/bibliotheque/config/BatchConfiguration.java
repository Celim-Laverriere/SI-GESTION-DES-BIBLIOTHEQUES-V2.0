package org.bibliotheque.config;

import org.bibliotheque.batch.CheckReservationTime;
import org.bibliotheque.batch.MailItemProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {


    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    private MailItemProcessor mailItemProcessor;

    @Autowired
    private CheckReservationTime checkReservationTime;

    @Bean
    protected Step processorMail() {
        return stepBuilderFactory
                .get("mailItemProcessor")
                .tasklet(mailItemProcessor)
                .build();
    }

    protected Step processorCheckReservationTime() {
        return stepBuilderFactory
                .get("checkReservationTime")
                .tasklet(checkReservationTime)
                .build();
    }

    @Bean
    public Job jobMail() {
        return jobBuilderFactory
                .get("jobMail")
                .incrementer(new RunIdIncrementer())
                .start(processorMail())
                .next(processorCheckReservationTime())
                .build();
    }

    @Bean
    public Job jobReservationTime() {
        return jobBuilderFactory
                .get("jobReservationTime")
                .incrementer(new RunIdIncrementer())
                .start(processorCheckReservationTime())
                .build();
    }

}
