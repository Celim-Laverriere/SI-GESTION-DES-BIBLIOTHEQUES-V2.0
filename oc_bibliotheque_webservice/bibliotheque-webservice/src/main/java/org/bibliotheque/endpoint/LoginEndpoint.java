package org.bibliotheque.endpoint;

import com.bibliotheque.gs_ws.*;
import lombok.NoArgsConstructor;
import org.bibliotheque.entity.CompteEntity;
import org.bibliotheque.service.contract.LoginService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import javax.transaction.Transactional;

@Endpoint
@NoArgsConstructor
public class LoginEndpoint {

    public static final String NAMESPACE_URI = "http://www.webservice.org/bibliotheque-ws";

    @Autowired
    private LoginService service;


    /**
     * Cette méthode vérifie les identifiants transmis par un client
     * @param request
     * @return Un statut de confirmation
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "loginRequest")
    @ResponsePayload
    @Transactional
    public LoginResponse loginResponse(@RequestPayload LoginRequest request){
        LoginResponse response = new LoginResponse();
        ServiceStatus serviceStatus = new ServiceStatus();
        CompteEntity compteEntity = service.getCompteByMailAndPassword(request.getMail(), request.getPassword());

        try{

            if (compteEntity.getMail().equals(request.getMail()) && compteEntity.getMotDePasse().equals(request.getPassword())){
                serviceStatus.setStatusCode("SUCCESS");
            }

        } catch (NullPointerException pEX){
            serviceStatus.setStatusCode("CONFLICT");
        }

        response.setServiceStatus(serviceStatus);
        return response;
    }


    /**
     * Cette méthode récupère les informations du compte d'un client connecté
     * @param request
     * @return Un compte
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCompteAfterLoginSuccessRequest")
    @ResponsePayload
    @Transactional
    public GetCompteAfterLoginSuccessResponse getCompteAfterLoginSuccessResponse(@RequestPayload GetCompteAfterLoginSuccessRequest request){
        GetCompteAfterLoginSuccessResponse response = new GetCompteAfterLoginSuccessResponse();
        CompteType compteType = new CompteType();
        CompteEntity compteEntity = service.getCompteByMail(request.getMail());

        BeanUtils.copyProperties(compteEntity, compteType);
        response.setCompteType(compteType);
        return response;
    }
}
