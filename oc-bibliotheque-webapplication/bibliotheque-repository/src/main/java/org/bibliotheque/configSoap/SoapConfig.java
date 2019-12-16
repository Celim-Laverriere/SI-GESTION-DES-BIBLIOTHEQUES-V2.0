package org.bibliotheque.configSoap;

import org.bibliotheque.client.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class SoapConfig {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // C'est le nom du paquet spécifié dans le champ <generatePackage> dans le pom.xml
        marshaller.setContextPath("org.bibliotheque.wsdl");
        return marshaller;
    }

    @Bean
    public LivreClient livreClient(Jaxb2Marshaller marshaller) {
        LivreClient client = new LivreClient();
        client.setDefaultUri("http://localhost:8088/ws/bibliotheque.wsdl");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }

    @Bean
    public CompteClient compteClient(Jaxb2Marshaller marshaller){
        CompteClient client = new CompteClient();
        client.setDefaultUri("http://localhost:8088/ws/bibliotheque.wsdl");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }

    @Bean
    public OuvrageClient ouvrageClient(Jaxb2Marshaller marshaller){
        OuvrageClient client = new OuvrageClient();
        client.setDefaultUri("http://localhost:8088/ws/bibliotheque.wsdl");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }

    @Bean
    public LoginClient loginClient(Jaxb2Marshaller marshaller){
        LoginClient client = new LoginClient();
        client.setDefaultUri("http://localhost:8088/ws/bibliotheque.wsdl");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }

    @Bean
    public EmpruntClient empruntClient(Jaxb2Marshaller marshaller){
        EmpruntClient client = new EmpruntClient();
        client.setDefaultUri("http://localhost:8088/ws/bibliotheque.wsdl");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }

    @Bean
    public SearchClient searchClient(Jaxb2Marshaller marshaller){
        SearchClient client = new SearchClient();
        client.setDefaultUri("http://localhost:8088/ws/bibliotheque.wsdl");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }

}
