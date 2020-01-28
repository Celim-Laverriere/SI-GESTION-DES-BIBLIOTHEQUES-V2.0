package org.bibliotheque.endpoint;

import com.bibliotheque.gs_ws.*;
import lombok.NoArgsConstructor;
import org.bibliotheque.entity.LivreEntity;
import org.bibliotheque.service.contract.LivreService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Endpoint
@NoArgsConstructor
public class LivreEndpoint {

    public static final String NAMESPACE_URI = "http://www.webservice.org/bibliotheque-ws";

    @Autowired
    private LivreService service;


    /**
     * Cette méthode récupère un livre par son identifiant
     * @param request
     * @return Un livre
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getLivreByIdRequest")
    @ResponsePayload
    @Transactional
    public GetLivreByIdResponse getLivreById(@RequestPayload GetLivreByIdRequest request){
        GetLivreByIdResponse response = new GetLivreByIdResponse();
        LivreEntity livreEntity = service.getLivreById(request.getLivreId());
        LivreType livreType = new LivreType();

        BeanUtils.copyProperties(livreEntity, livreType);
        response.setLivreType(livreType);
        return response;
    }


    /**
     * Cette méthode récupère tous les livres
     * @param request
     * @return Liste de livres
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllLivresRequest")
    @ResponsePayload
    public GetAllLivresResponse getAllLivre(@RequestPayload GetAllLivresRequest request){
        GetAllLivresResponse response = new GetAllLivresResponse();
        List<LivreType> livreTypeList = new ArrayList<LivreType>();
        List<LivreEntity> livreEntityList = service.getAllLivres();

        for (LivreEntity entity : livreEntityList){
            LivreType livreType = new LivreType();
            BeanUtils.copyProperties(entity, livreType);
            livreTypeList.add(livreType);
        }
        response.getLivreType().addAll(livreTypeList);
        return response;
    }


    /**
     * Cette méthode ajoute un livre
     * @param request
     * @return
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addLivreRequest")
    @ResponsePayload
    public AddLivreResponse addLivre(@RequestPayload AddLivreRequest request) {
        AddLivreResponse response = new AddLivreResponse();
        LivreType newLivreType = new LivreType();
        ServiceStatus serviceStatus = new ServiceStatus();
        LivreEntity newLivreEntity = new LivreEntity();

        BeanUtils.copyProperties(request.getLivreType(), newLivreEntity);

        try{
            LivreEntity savedLivreEntity = service.addLivre(newLivreEntity);
            BeanUtils.copyProperties(savedLivreEntity, newLivreType);

            serviceStatus.setStatusCode("SUCCESS");
            serviceStatus.setMessage("Content Added Successfully");

        } catch (DataIntegrityViolationException pEX) {
            serviceStatus.setStatusCode("CONFLICT");
            serviceStatus.setMessage("Exception while adding Entity");
        } catch (Exception pEX) {
            pEX.printStackTrace();
        }

        response.setLivreType(newLivreType);
        response.setServiceStatus(serviceStatus);
        return response;
    }


    /**
     * Cette méthode met à jour un livre
     * @param request
     * @return
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateLivreRequest")
    @ResponsePayload
    public UpdateLivreResponse updateLivre(@RequestPayload UpdateLivreRequest request){
        UpdateLivreResponse response = new UpdateLivreResponse();
        ServiceStatus serviceStatus = new ServiceStatus();

        try {
            // 1. Trouver si le livre est disponible
            LivreEntity upLivre = service.getLivreById(request.getLivreType().getId());

            // 2. Obtenir les informations du livre à mettre à jour à partir de la requête
            BeanUtils.copyProperties(request.getLivreType(), upLivre);

            try {
                // 3. met à jour le livre dans la base de données
               service.updateLivre(upLivre);

                serviceStatus.setStatusCode("SUCCESS");
                serviceStatus.setMessage("Content updated Successfully");

            } catch (DataIntegrityViolationException pEX) {
                serviceStatus.setStatusCode("CONFLICT");
                serviceStatus.setMessage("Exception while updating Entity : " + request.getLivreType().getRefBibliotheque());
            }  catch (Exception pEX) {
                pEX.printStackTrace();
            }

        } catch (NoSuchElementException pEX) {
            serviceStatus.setStatusCode("NOT FOUND");
            serviceStatus.setMessage("Livre : " + request.getLivreType().getRefBibliotheque() + " not found");
        } catch (Exception pEX) {
            pEX.printStackTrace();
        }

        response.setServiceStatus(serviceStatus);
        return response;
    }


    /**
     * Cette méthode supprime un livre
     * @param request
     * @return
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteLivreRequest")
    @ResponsePayload
    public DeleteLivreResponse deleteLivre(@RequestPayload DeleteLivreRequest request){
        DeleteLivreResponse response = new DeleteLivreResponse();
        ServiceStatus serviceStatus = new ServiceStatus();

        try {
            service.deleteLivre(request.getLivreId());

            serviceStatus.setStatusCode("SUCCESS");
            serviceStatus.setMessage("Content Deleted Successfully");

        } catch (EmptyResultDataAccessException pEX) {
            serviceStatus.setStatusCode("FAIL");
            serviceStatus.setMessage("Exception while deletint Entity id : " + request.getLivreId());
        } catch (Exception pEX) {
            pEX.printStackTrace();
        }

        response.setServiceStatus(serviceStatus);
        return response;
    }
}
