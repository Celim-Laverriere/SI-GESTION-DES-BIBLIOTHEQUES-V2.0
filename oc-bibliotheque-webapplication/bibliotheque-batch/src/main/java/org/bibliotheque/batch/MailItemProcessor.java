package org.bibliotheque.batch;

import org.bibliotheque.Mail.SendingMailThroughGmailSMTPServer;
import org.bibliotheque.service.CompteService;
import org.bibliotheque.service.EmpruntService;
import org.bibliotheque.service.LivreService;
import org.bibliotheque.service.OuvrageService;
import org.bibliotheque.wsdl.CompteType;
import org.bibliotheque.wsdl.EmpruntType;
import org.bibliotheque.wsdl.LivreType;
import org.bibliotheque.wsdl.OuvrageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


@Component
public class MailItemProcessor implements Tasklet, StepExecutionListener {

    private static final Logger logger = LoggerFactory.getLogger(MailItemProcessor.class);

    @Autowired
    private EmpruntService empruntService;

    @Autowired
    private CompteService compteService;

    @Autowired
    private LivreService livreService;

    @Autowired
    private OuvrageService ouvrageService;

    @Autowired
    private SendingMailThroughGmailSMTPServer sendingMailThroughGmailSMTPServer;


    /**
     * CETTE METHODE A POUR ROLE DE CONTROLER LES DATES DE FIN DES EMPRUNTS EN FONCTION DE LA DATE COURANTE, ET AU CAS
     * ECHEANT DE TRAITER LES EMPRUNTS NON RENDUS DANS LE DELAI LEGAL. POUR CE FAIRE UN E-MAIL DE RAPPEL EST ENVOYE VIA
     * LA CLASSE "sendingmailthroughgmailsmtpserver".
     * @return
     * @throws Exception
     */
    @Override
    public RepeatStatus execute(StepContribution stepContribution,
                                ChunkContext chunkContext) throws Exception {

        /**@see EmpruntService#getAllEmprunt()*/
        List<EmpruntType> empruntTypeList = empruntService.getAllEmprunt();

        // La variable "nombreDeJours" a pour rôle de comptabiliser le nombre de jours que l'utilisateur a dépassé.
        Long nombreDeJours;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        LocalDateTime cuttentTime = LocalDateTime.now();
        Date dateToDay = dateFormat.parse(dateFormat.format(dateFormat.parse(cuttentTime.toLocalDate().toString())));

        for (EmpruntType empruntType : empruntTypeList){

            Date empruntDateFin = dateFormat.parse(dateFormat.format(dateFormat.parse(empruntType.getDateFin().toString())));

          if (empruntDateFin.after(dateToDay)){

              nombreDeJours = (empruntDateFin.getTime() - dateToDay.getTime())/(1000*60*60*24);

          } else if(empruntDateFin.before(dateToDay) && empruntType.getStatut().equals("En cours")){

              /**@see CompteService#compteById(Integer)*/
              CompteType compteType = compteService.compteById(empruntType.getCompteId());

              /**@see LivreService#livreById(Integer)*/
              LivreType livreType = livreService.livreById(empruntType.getLivreId());

              /**@see OuvrageService#ouvrageById(Integer)*/
              OuvrageType ouvrageType = ouvrageService.ouvrageById(livreType.getOuvrageId());

              nombreDeJours = (empruntDateFin.getTime() - dateToDay.getTime())/(1000*60*60*24);
              String nombreDeJoursStr = String.valueOf(nombreDeJours).replace("-", "");

              logger.warn("E-mail de relance : {}", "Date de fin d'emprunt : "
                      + dateFormat.format(dateFormat.parse(empruntType.getDateFin().toString())) + " -"
                      + " Référence du livre : " + livreType.getRefBibliotheque());

              String subject = "Restitution du livre : " + ouvrageType.getTitre();
              String text = textMail(compteType, ouvrageType, livreType, nombreDeJoursStr);

              /**@see SendingMailThroughGmailSMTPServer#sendMessage(String, String, String, String)*/
              sendingMailThroughGmailSMTPServer.sendMessage(subject, text, compteType.getMail(), ouvrageType.getPhotos().get(0).getUrlPhoto());

          } else if (dateToDay.equals(empruntDateFin)){
              nombreDeJours = (empruntDateFin.getTime() - dateToDay.getTime())/(1000*60*60*24);

          } else {
              System.out.println("How to get here?");
          }

        }

        return RepeatStatus.FINISHED;
    }


    @Override
    public void beforeStep(StepExecution stepExecution) {
    }


    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return ExitStatus.COMPLETED;
    }


    public String textMail(CompteType compteType, OuvrageType ouvrageType, LivreType livreType, String nombreDeJoursStr){

        return ""
                + "<html>"
                + " <body>"
                + "  <h1>Demande de réstitution</h1>"
                + "<hr/>"
                + "<div id=\"conteneur\" style=\" display:flex; width:70%; margin:auto\">"
                + "     <div style=\"\">"
                + "         <img style=\"display: inline-block; height: 300px; width: 200px; vertical-align: top\" src=\"cid:image-id\" />"
                + "     </div>"
                + "     <div style=\"margin-left: 20px; border-style: solid; border-bottom: white; border-top: "
                + "white; border-right: white; border-color: #DCDCDC; border-width: 2px;\">"
                + ""
                + "<div style=\"padding-left: 20px\">"
                + "<p>Bonjour, " + compteType.getPrenom() + "</p>"
                + "<p>Vous devez vous présenter à la bibliothèque pour rendre le livre que vous avez emprunté!</p>"
                + "<p>En effet, vous avez déjà dépassé le délai auquel vous deviez restituer votre emprunt de " + nombreDeJoursStr + " jour(s)!</p>"
                + "<p>Titre de l'ouvrage :  " + ouvrageType.getTitre() + "</p>"
                + "<p>Référence du livre:  " + livreType.getRefBibliotheque() + "</p>"
                + "<p>Merci pour votre compréhension. À très bientôt dans votre bibliothèque préféré!</p>"
                + "</div>"
                + ""
                + "</div>"
                + "</div>"
                + "<hr/>"
                + "<div style=\"margin:auto; text-align:center; width:70%\">"
                + "<h4><a href=\"http://localhost:8080/\">Bibliothéque de Tilly</a></h4>"
                + "<small>Adresse : 124 Rue Frédéric-Magisson, 80770 Tilly</small></br>"
                + "<small>La bibliothéque est ouverte du lundi au samedi de 9h00 à 18h00</small></br>"
                + "<small>Téléphone : 06 00 64 59 12</small></br>"
                + "<small>Email : tilly.bibliothéque@gmail.com</small></br>"
                + "</div>"
                + " </body>"
                + "</html>";
    }
}
