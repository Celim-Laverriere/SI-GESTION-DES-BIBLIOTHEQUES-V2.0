package org.bibliotheque.client;

import org.bibliotheque.wsdl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.SoapFaultClientException;

public class LivreClient extends WebServiceGatewaySupport {

    private static final Logger logger = LoggerFactory.getLogger(LivreClient.class);


    /**
     * ==== CETTE METHODE RECUPERER UN LIVRE PAR SON IDENTIFIANT ====
     * @param id
     * @return LivreType
     */
    public GetLivreByIdResponse getLivreById(Integer id) {

        GetLivreByIdResponse response = new GetLivreByIdResponse();

        try{
            GetLivreByIdRequest request = new GetLivreByIdRequest();
            request.setLivreId(id);
            response = (GetLivreByIdResponse) getWebServiceTemplate().marshalSendAndReceive(request);
        } catch (SoapFaultClientException pEX){
            logger.error("GetLivreByIdResponse : {}", pEX.toString());
        }

        return response;
    }

}
