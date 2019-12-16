package org.bibliotheque.client;

import org.bibliotheque.wsdl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.SoapFaultClientException;

public class CompteClient extends WebServiceGatewaySupport {

    private static final Logger logger = LoggerFactory.getLogger(OuvrageClient.class);


    /**
     * ==== CETTE METHODE RECUPERER UN COMPTE CLIENT PAR SON IDENTIFIANT ====
     * @param id
     * @return LES INFORMATIONS D'UN COMPTE
     */
    public GetCompteByIdResponse getCompteById(Integer id){

        GetCompteByIdResponse response = new GetCompteByIdResponse();

        try{
            GetCompteByIdRequest request = new GetCompteByIdRequest();
            request.setCompteId(id);
            response = (GetCompteByIdResponse) getWebServiceTemplate().marshalSendAndReceive(request);
        } catch (SoapFaultClientException pEX){
            logger.error("GetCompteByIdResponse : {}", pEX.toString());
        }

        return response;
    }


    /**
     * ==== CETTE METHODE MET A JOUR LE COMPTE UTILISATEUR ====
     * @param compteType
     * @return UN MESSAGE DE CONFIRMATION
     */
    public UpdateCompteResponse updateCompte(CompteType compteType){
        UpdateCompteRequest request = new UpdateCompteRequest();
        request.setCompteType(compteType);
        return (UpdateCompteResponse) getWebServiceTemplate().marshalSendAndReceive(request);
    }


}
