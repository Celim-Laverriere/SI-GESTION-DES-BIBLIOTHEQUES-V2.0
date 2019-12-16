package org.bibliotheque.client;

import org.bibliotheque.wsdl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.SoapFaultClientException;

public class LoginClient extends WebServiceGatewaySupport {

    private static final Logger logger = LoggerFactory.getLogger(OuvrageClient.class);

    /**
     *  ==== GET COMPTE BY MAIL AND PASSWORD FOR LOGIN ====
     * @param mail
     * @param password
     * @return LoginResponse
     */
    public LoginResponse login(String mail, String password){

        LoginResponse response = new LoginResponse();

        try{
            LoginRequest request = new LoginRequest();
            request.setMail(mail);
            request.setPassword(password);
            response = (LoginResponse) getWebServiceTemplate().marshalSendAndReceive(request);

            logger.info("LoginResponse : {}", response.getServiceStatus().getStatusCode());

        } catch (SoapFaultClientException pEX){
            logger.error("LoginResponse : {}", pEX.toString());
            ServiceStatus serviceStatus = new ServiceStatus();
            serviceStatus.setStatusCode("CONFLICT");
            response.setServiceStatus(serviceStatus);
        }

        return response;
    }

    /**
     * ==== GET COMPTE AFTER LOGIN SUCCESS ====
     * @param mail
     * @return GetCompteAfterLoginSuccessResponse
     */
    public GetCompteAfterLoginSuccessResponse getCompteAfterLoginSuccess(String mail){

        GetCompteAfterLoginSuccessResponse response = new GetCompteAfterLoginSuccessResponse();

        try{
            GetCompteAfterLoginSuccessRequest request = new GetCompteAfterLoginSuccessRequest();
            request.setMail(mail);
            response = (GetCompteAfterLoginSuccessResponse) getWebServiceTemplate().marshalSendAndReceive(request);

        } catch (SoapFaultClientException pEX){
            logger.error("GetCompteAfterLoginSuccessResponse : {}", pEX.toString());
        }

        return response;
    }
}
