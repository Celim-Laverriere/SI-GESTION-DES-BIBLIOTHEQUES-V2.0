package org.bibliotheque;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class LauncherBatch{

    private static final Logger logger = LoggerFactory.getLogger(LauncherBatch.class);

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job job;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(LauncherBatch.class, args);
    }


    // Le batch est paramétré pour s'exécuter toutes les 24h00
    @Scheduled(cron = "0 0 */24 * * ?")
    public void perform() throws Exception {

        JobParameters params = new JobParametersBuilder()
                .addString("", String.valueOf(System.currentTimeMillis()))
                .toJobParameters();
        jobLauncher.run(job, params);
    }

}
