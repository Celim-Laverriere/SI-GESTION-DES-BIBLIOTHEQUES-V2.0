package org.bibliotheque.Mail;

import com.sun.mail.smtp.SMTPMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import java.io.InputStream;
import java.util.Properties;

@Component
public class SendingMailThroughGmailSMTPServer {

    private static final Logger logger = LoggerFactory.getLogger(SendingMailThroughGmailSMTPServer.class);

    public void sendMessage(String subject, String text, String destinataire, String image){

        final Properties propUrl = new Properties();
        InputStream stream = null;

        final String username = "oc.bibliotheque.projet7@gmail.com";
        final String password = "bibliothequeprojet7";

        String pathImage = null;

        try{
            String filename = "urlImageBatch.properties";
            stream = getClass().getClassLoader().getResourceAsStream(filename);
            propUrl.load(stream);
            pathImage = propUrl.getProperty("url.image") + image;
        } catch (Exception pEX){
            logger.error("sendMessage - pathImage : {}", pEX.toString());
        }


        // 1 -> Création de la session
        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.host", "smtp.gmail.com");
        properties.setProperty("mail.smtp.port", "587");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        Session session = Session.getInstance(properties);


        // 2 -> Création du message
        MimeMultipart content = new MimeMultipart("related");
        MimeBodyPart htmlPart = new MimeBodyPart();
        try {

        htmlPart.setText(text,"UTF-8", "html");

        content.addBodyPart(htmlPart);

        MimeBodyPart imagePart = new MimeBodyPart();

            DataSource source = new FileDataSource(pathImage);
            imagePart.setDataHandler(new DataHandler(source));
            imagePart.setHeader("Content-ID", "image-id");
            content.addBodyPart(imagePart);

        } catch (MessagingException pEX){
            pEX.printStackTrace();
        }

        SMTPMessage message = new SMTPMessage(session);
        try {
            message.setContent(content);
            message.setSubject(subject);
            message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(destinataire));
            message.addRecipients(Message.RecipientType.CC, InternetAddress.parse(username));
        } catch (MessagingException pEX){

        }


        // 3 -> Envoi du message
        Transport transport = null;
        try{

            transport = session.getTransport("smtp");
            transport.connect(username, password);
            transport.sendMessage(message, new Address[]{
                    new InternetAddress(destinataire), new InternetAddress(username)});

        } catch (MessagingException pEX){
            pEX.printStackTrace();
        } finally {
            try {
                if (transport != null){
                    transport.close();
                }
            } catch (MessagingException pEX){
                pEX.printStackTrace();
            }
        }
    }
}
