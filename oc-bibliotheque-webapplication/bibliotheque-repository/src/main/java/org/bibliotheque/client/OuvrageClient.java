package org.bibliotheque.client;

import org.bibliotheque.wsdl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.SoapFaultClientException;

public class OuvrageClient extends WebServiceGatewaySupport {

    private static final Logger logger = LoggerFactory.getLogger(OuvrageClient.class);


    /**
     * ==== CETTE METHODE RECUPERER UN OUVRAGE PAR SON IDENTIFIANT ====
     * @param id
     * @return GetOuvrageByIdResponse
     */
    public GetOuvrageByIdResponse getOuvrageById(Integer id){

        GetOuvrageByIdResponse response = new GetOuvrageByIdResponse();

        try{
            GetOuvrageByIdRequest request = new GetOuvrageByIdRequest();
            request.setOuvrageId(id);
            response = (GetOuvrageByIdResponse) getWebServiceTemplate().marshalSendAndReceive(request);

        } catch (SoapFaultClientException pEX){
            logger.error("GetOuvrageByIdResponse : {}", pEX.toString());
        }

        return response;
    }


    /**
     * ==== CETTE METHODE RECUPERER TOUS LES OUVRAGES ====
     * @return GetAllOuvragesResponse
     */
    public GetAllOuvragesResponse getAllOuvrages(){

        GetAllOuvragesResponse response = new GetAllOuvragesResponse();

        try{
            GetAllOuvragesRequest request = new GetAllOuvragesRequest();
            response = (GetAllOuvragesResponse) getWebServiceTemplate().marshalSendAndReceive(request);

        } catch (SoapFaultClientException pEX){
            logger.error("GetAllOuvragesResponse : {}", pEX.toString());
        }

        return response;
    }

}
