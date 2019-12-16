package org.bibliotheque.endpoint;

import com.bibliotheque.gs_ws.*;
import lombok.NoArgsConstructor;
import org.bibliotheque.entity.CompteEntity;
import org.bibliotheque.service.contract.CompteService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import java.util.ArrayList;
import java.util.List;

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
        CompteEntity compteEntity = service.getCompteById(request.getCompteId());
        CompteType compteType = new CompteType();
        BeanUtils.copyProperties(compteEntity, compteType);
        response.setCompteType(compteType);
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
        CompteEntity savedCompteEntity = service.addCompte(newCompteEntity);

        if (savedCompteEntity == null){
            serviceStatus.setStatusCode("CONFLICT");
            serviceStatus.setMessage("Exception while adding Entity");
        } else {

            BeanUtils.copyProperties(savedCompteEntity, newCompteType);
            serviceStatus.setStatusCode("SUCCESS");
            serviceStatus.setMessage("Content Added Successfully");
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

        // 1. Trouver si le compte est disponible
        CompteEntity upCompte = service.getCompteById(request.getCompteType().getId());

        if(upCompte == null){
            serviceStatus.setStatusCode("NOT FOUND");
            serviceStatus.setMessage("Compte : " + request.getCompteType().getNom() + " " +
                    request.getCompteType().getPrenom() + " " + " not found");
        } else {

            // 2. Obtenir les informations du compte à mettre à jour à partir de la requête
            BeanUtils.copyProperties(request.getCompteType(), upCompte);

            // 3. Mettre à jour le compte dans la base de données
            boolean flag = service.updateCompte(upCompte);

            if(flag == false){
                serviceStatus.setStatusCode("CONFLICT");
                serviceStatus.setMessage("Exception while updating Entity : " + request.getCompteType().getNom() + " " +
                        request.getCompteType().getPrenom());
            } else {
                serviceStatus.setStatusCode("SUCCESS");
                serviceStatus.setMessage("Content updated Successfully");
            }
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

        boolean flag = service.deleteCompte(request.getCompteId());

        if (flag == false){
            serviceStatus.setStatusCode("FAIL");
            serviceStatus.setMessage("Exception while deletint Entity id : " + request.getCompteId());
        } else {
            serviceStatus.setStatusCode("SUCCESS");
            serviceStatus.setMessage("Content Deleted Successfully");
        }

        response.setServiceStatus(serviceStatus);
        return response;
    }

}
