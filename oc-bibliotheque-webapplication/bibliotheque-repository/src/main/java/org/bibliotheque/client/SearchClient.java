package org.bibliotheque.client;

import org.bibliotheque.wsdl.GetSearchByKeywordRequest;
import org.bibliotheque.wsdl.GetSearchByKeywordResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.SoapFaultClientException;

public class SearchClient extends WebServiceGatewaySupport {

    private static final Logger logger = LoggerFactory.getLogger(OuvrageClient.class);

    /**
     * ==== CETTE METHODE RECUPERER UN OU UNE LISTE D'OUVRAGE(S) PAR MOT-CLE ====
     * @param keyword
     * @return GetSearchByKeywordResponse
     */
    public GetSearchByKeywordResponse getSearchByKeyword(String keyword){

        GetSearchByKeywordResponse response = new GetSearchByKeywordResponse();

        try{
            GetSearchByKeywordRequest request = new GetSearchByKeywordRequest();
            request.setKeyword(keyword);
            response = (GetSearchByKeywordResponse) getWebServiceTemplate().marshalSendAndReceive(request);

        } catch (SoapFaultClientException pEX){
            logger.error("Metho GetSearchByKeywordResponse :  {}", pEX.toString());
        }
        return response;
    }

}
