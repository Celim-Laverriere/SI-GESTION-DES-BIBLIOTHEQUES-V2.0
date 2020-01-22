package org.bibliotheque.endpoint;

import com.bibliotheque.gs_ws.*;
import lombok.NoArgsConstructor;
import org.bibliotheque.entity.CompteEntity;
import org.bibliotheque.service.contract.CompteService;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Endpoint
@NoArgsConstructor
public class CompteEndpoint {

    public static final String NAMESPACE_URI = "http://www.webservice.org/bibliotheque-ws";

    @Autowired
    private CompteService service;


    /**
     * Cette méthode récupère un compte utilisateur par son identifiant
     * @param request
     * @return Un compte
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCompteByIdRequest")
    @ResponsePayload
    public GetCompteByIdResponse getCompteById(@RequestPayload GetCompteByIdRequest request){
        GetCompteByIdResponse response = new GetCompteByIdResponse();
        CompteType compteType = new CompteType();
        ServiceStatus serviceStatus = new ServiceStatus();

        try {
            CompteEntity compteEntity = service.getCompteById(request.getCompteId());

            BeanUtils.copyProperties(compteEntity, compteType);

        }  catch (NoSuchElementException pEX) {
            serviceStatus.setStatusCode("NOT FOUND");
            serviceStatus.setMessage("Compte " + request.getCompteId() + " : not found");
        } catch (Exception pEX) {
            pEX.printStackTrace();
        }

        response.setCompteType(compteType);
        response.setServiceStatus(serviceStatus);
        return response;
    }


    /**
     * Cette méthode récupère tous les comptes utilisateurs
     * @param request
     * @return Liste de comptes
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllComptesRequest")
    @ResponsePayload
    public GetAllComptesResponse getAllComptes(@RequestPayload GetAllComptesRequest request){
        GetAllComptesResponse response = new GetAllComptesResponse();
        List<CompteType> compteTypeList = new ArrayList<>();
        List<CompteEntity> compteEntityList = service.getAllComptes();

        for (CompteEntity entity : compteEntityList){
            CompteType compteType = new CompteType();
            BeanUtils.copyProperties(entity, compteType);
            compteTypeList.add(compteType);
        }

        response.getCompteType().addAll(compteTypeList);
        return response;
    }


    /**
     * Cette méthode ajoute un compte client
     * @param request
     * @return
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addCompteRequest")
    @ResponsePayload
    public AddCompteResponse addCompte(@RequestPayload AddCompteRequest request){
        AddCompteResponse response = new AddCompteResponse();
        CompteType newCompteType = new CompteType();
        ServiceStatus serviceStatus = new ServiceStatus();
        CompteEntity newCompteEntity = new CompteEntity();

        BeanUtils.copyProperties(request.getCompteType(), newCompteEntity);

        try{
            CompteEntity savedCompteEntity = service.addCompte(newCompteEntity);

            BeanUtils.copyProperties(savedCompteEntity, newCompteType);
            serviceStatus.setStatusCode("SUCCESS");
            serviceStatus.setMessage("Content Added Successfully");

        } catch (DataIntegrityViolationException pEX){
            serviceStatus.setStatusCode("CONFLICT");
            serviceStatus.setMessage("Exception while adding Entity");
        } catch (Exception pEX){
            pEX.printStackTrace();
        }

        response.setCompteType(newCompteType);
        response.setServiceStatus(serviceStatus);
        return response;
    }


    /**
     * Cette méthode met à jour un compte client
     * @param request
     * @return
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateCompteRequest")
    @ResponsePayload
    public UpdateCompteResponse updateCompte(@RequestPayload UpdateCompteRequest request){
        UpdateCompteResponse response = new UpdateCompteResponse();
        ServiceStatus serviceStatus = new ServiceStatus();

        try{
            // 1. Trouver si le compte est disponible
            CompteEntity  upCompte = service.getCompteById(request.getCompteType().getId());

            // 2. Obtenir les informations du compte à mettre à jour à partir de la requête
            BeanUtils.copyProperties(request.getCompteType(), upCompte);

            try {
                // 3. Mettre à jour le compte dans la base de données
                service.updateCompte(upCompte);

                serviceStatus.setStatusCode("SUCCESS");
                serviceStatus.setMessage("Content updated Successfully");

            } catch (DataIntegrityViolationException pEX) {
                System.out.println(pEX);
                serviceStatus.setStatusCode("CONFLICT");
                serviceStatus.setMessage("Exception while updating Entity : " + request.getCompteType().getNom() + " " +
                        request.getCompteType().getPrenom());
            } catch (Exception pEX) {
                pEX.printStackTrace();
            }

        } catch (NoSuchElementException pEX) {
            serviceStatus.setStatusCode("NOT FOUND");
            serviceStatus.setMessage("Compte : " + request.getCompteType().getNom() + " " +
                    request.getCompteType().getPrenom() + " not found");
        } catch (Exception pEX) {
            pEX.printStackTrace();
        }

        response.setServiceStatus(serviceStatus);
        return response;
    }


    /**
     * Cette méthode supprime un compte client
     * @param request
     * @return
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteCompteRequest")
    @ResponsePayload
    public DeleteCompteResponse deleteCompte(@RequestPayload DeleteCompteRequest request){
        DeleteCompteResponse response = new DeleteCompteResponse();
        ServiceStatus serviceStatus = new ServiceStatus();

        try {
            service.deleteCompte(request.getCompteId());

            serviceStatus.setStatusCode("SUCCESS");
            serviceStatus.setMessage("Content Deleted Successfully");

        } catch (EmptyResultDataAccessException pEX) {
            serviceStatus.setStatusCode("FAIL");
            serviceStatus.setMessage("Exception while deletint Entity id : " + request.getCompteId());
        } catch (Exception pEX) {
            pEX.printStackTrace();
        }

        response.setServiceStatus(serviceStatus);
        return response;
    }

}
