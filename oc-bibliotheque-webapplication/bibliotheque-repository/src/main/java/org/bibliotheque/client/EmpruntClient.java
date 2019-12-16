package org.bibliotheque.client;

import org.bibliotheque.wsdl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.SoapFaultClientException;

public class EmpruntClient extends WebServiceGatewaySupport {

    private static final Logger logger = LoggerFactory.getLogger(OuvrageClient.class);


    /**
     * ==== CETTE METHODE RECUPERER TOUS LES EMPRUNTS ====
     * @return LA LISTE DES EMPRUNTS
     */
    public GetAllEmpruntResponse getAllEmprunt(){
        GetAllEmpruntResponse response = new GetAllEmpruntResponse();

        try{
            GetAllEmpruntRequest request = new GetAllEmpruntRequest();
            response = (GetAllEmpruntResponse) getWebServiceTemplate().marshalSendAndReceive(request);
        } catch (SoapFaultClientException pEX){
            logger.error("Methode GetAllEmpruntResponse : {}", pEX.toString());
        }

        return response;
    }

    /**
     * ==== CETTE METHODE RECUPERER TOUS LES EMPRUNTS D'UN CLIENT ====
     * @param id
     * @return UNE LISTE DES EMPRUNTS D'UN COMPTE
     */
    public GetAllEmpruntByCompteIdResponse getAllEmpruntByCompteId(Integer id){

        GetAllEmpruntByCompteIdResponse response = new GetAllEmpruntByCompteIdResponse();

        try{
            GetAllEmpruntByCompteIdRequest request = new GetAllEmpruntByCompteIdRequest();
            request.setId(id);
            response = (GetAllEmpruntByCompteIdResponse) getWebServiceTemplate().marshalSendAndReceive(request);

        } catch (SoapFaultClientException pEX){
            logger.error("GetAllEmpruntByCompteIdResponse : {}", pEX.toString());
        } catch (Exception pEX){

        }

        return response;
    }


    /**
     * ==== CETTE METHODE RECUPERER UN EMPRUNT PAR SON IDENTIFIANT ====
     * @param id
     * @return UN EMPRUNT
     */
    public GetEmpruntByIdResponse getEmpruntById(Integer id){

        GetEmpruntByIdResponse response = new GetEmpruntByIdResponse();

        try{
            GetEmpruntByIdRequest request = new GetEmpruntByIdRequest();
            request.setEmpruntId(id);
            response = (GetEmpruntByIdResponse) getWebServiceTemplate().marshalSendAndReceive(request);
        } catch (SoapFaultClientException pEX){
            logger.error("GetEmpruntByIdResponse : {}", pEX.toString());
        }

        return response;
    }


    /**
     * ==== CETTE METHODE MET A JOUR UN EMPRUNT POUR PROLONGER SA DUREE ====
     * @param empruntType
     * @return UN STATUT-CODE DE CONFIRMATION
     */
    public UpdateEmpruntResponse updateEmprunt(EmpruntType empruntType){

        UpdateEmpruntResponse response = new UpdateEmpruntResponse();

        try{
            UpdateEmpruntRequest request = new UpdateEmpruntRequest();
            request.setEmpruntType(empruntType);
            response = (UpdateEmpruntResponse) getWebServiceTemplate().marshalSendAndReceive(request);

        } catch (SoapFaultClientException pEX){
            logger.error("UpdateEmpruntResponse : {}", pEX.toString());
        }

        return response;
    }
}
