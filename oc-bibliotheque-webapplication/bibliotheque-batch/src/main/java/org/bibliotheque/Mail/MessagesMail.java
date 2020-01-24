package org.bibliotheque.Mail;

import org.bibliotheque.wsdl.CompteType;
import org.bibliotheque.wsdl.OuvrageType;
import org.springframework.stereotype.Component;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Component
public class MessagesMail {


    /**
     *
     * @param dateOuvrageDisponible
     * @param compteType
     * @param ouvrageType
     * @return
     */
    public String textMailDisponible(Date dateOuvrageDisponible, CompteType compteType, OuvrageType ouvrageType){

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        return textMail().get(0)
                + "<div style=\"padding-left: 20px\">"
                + "<p>Bonjour, " + compteType.getPrenom() + "</p>"
                + "<p>Votre livre est disponible !</p>"
                + "<p>Vous pouvez vous présenter à l'accueil de la bibliothéque pour venir le récupérer !</p>"
                + "<p>Titre de l'ouvrage :  " + ouvrageType.getTitre() + "</p>"
                + "<p>À très bientôt dans votre bibliothèque préféré pour de nouvelles lécture !</p>"
                + "<p>Rappel : vous avez 48 heures pour venir récupérer votre livre, passé ce délai votre réservation sera annulé.</p>"
                + "</div>"
                + textMail().get(1);
    }


    /**
     *
     * @param compteType
     * @param ouvrageType
     * @return
     */
    public String textMailDelaiExpirer(CompteType compteType, OuvrageType ouvrageType) {

        return textMail().get(0)
                + "<div style=\"padding-left: 20px\">"
                + "<p>Bonjour, " + compteType.getPrenom() + "</p>"
                + "<p>Nous vous informons que votre réservation a expiré le délai de 48h00 pour venir retirer votre livre en bibliothèque.</p>"
                + "<p>Titre de l'ouvrage :  " + ouvrageType.getTitre() + "</p>"
                + "<p>À très bientôt dans votre bibliothèque préféré pour de nouvelles lécture !</p>"
                + "</div>"
                + textMail().get(1);
    }


    /**
     *
     * @return
     */
    public List<String> textMail(){

        Properties properties = new Properties();
        InputStream stream = null;
        List<String> pathMessage = new ArrayList<>();

        try{
            String filename = "messagesMail.properties";
            stream = getClass().getClassLoader().getResourceAsStream(filename);
            properties.load(stream);
            pathMessage.add(properties.getProperty("mailPartie1"));
            pathMessage.add(properties.getProperty("adresse_bibliothèque"));
        } catch (Exception pEX){
            System.out.println(pEX);
        }

        return pathMessage;
    }
}
