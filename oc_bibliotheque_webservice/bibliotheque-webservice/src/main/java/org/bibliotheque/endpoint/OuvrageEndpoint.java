package org.bibliotheque.endpoint;

import com.bibliotheque.gs_ws.*;
import lombok.NoArgsConstructor;
import org.bibliotheque.entity.LivreEntity;
import org.bibliotheque.entity.OuvrageEntity;
import org.bibliotheque.entity.PhotoEntity;
import org.bibliotheque.service.contract.OuvrageService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Endpoint
@NoArgsConstructor
public class OuvrageEndpoint {

    public static final String NAMESPACE_URI = "http://www.webservice.org/bibliotheque-ws";

    @Autowired
    private OuvrageService service;


    /**
     * Cette méthode récupère un ouvrage par son identifiant
     * @param request
     * @return Un ouvrage
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getOuvrageByIdRequest")
    @ResponsePayload
    @Transactional
    public GetOuvrageByIdResponse getOuvrageById(@RequestPayload GetOuvrageByIdRequest request){
        GetOuvrageByIdResponse response = new GetOuvrageByIdResponse();
        OuvrageEntity ouvrageEntity = service.getOuvrageById(request.getOuvrageId());
        OuvrageType ouvrageType = new OuvrageType();

        // Récupération de la liste des livres de l'ouvrage
        for (LivreEntity livreEntity : ouvrageEntity.getLivres()) {
            LivreType livreType = new LivreType();
            BeanUtils.copyProperties(livreEntity, livreType);
            ouvrageType.getLivres().add(livreType);
        }

        // Récupération de la liste des photos de l'ouvrage
        for (PhotoEntity photoEntity: ouvrageEntity.getPhotos()){
            PhotoType photoType = new PhotoType();
            BeanUtils.copyProperties(photoEntity, photoType);
            ouvrageType.getPhotos().add(photoType);
        }

        BeanUtils.copyProperties(ouvrageEntity, ouvrageType);
        response.setOuvrageType(ouvrageType);
        return response;
    }

    /**
     * Cette méthode récupère tous les ouvrages
     * @param request
     * @return Une liste d'ouvrages
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllOuvragesRequest")
    @ResponsePayload
    @Transactional
    public GetAllOuvragesResponse getAllOuvrages(@RequestPayload GetAllOuvragesRequest request){
        GetAllOuvragesResponse response = new GetAllOuvragesResponse();
        List<OuvrageType> ouvrageTypeList = new ArrayList<>();
        List<OuvrageEntity> ouvrageEntityList = service.getAllOuvrages();

        for (OuvrageEntity entity : ouvrageEntityList){
            OuvrageType ouvrageType = new OuvrageType();

            for (LivreEntity livreEntity : entity.getLivres()){
                LivreType livreType = new LivreType();

                BeanUtils.copyProperties(livreEntity, livreType);
                ouvrageType.getLivres().add(livreType);
            }

            for (PhotoEntity photoEntity : entity.getPhotos()){
                PhotoType photoType = new PhotoType();

                BeanUtils.copyProperties(photoEntity, photoType);
                ouvrageType.getPhotos().add(photoType);
            }

            BeanUtils.copyProperties(entity, ouvrageType);
            ouvrageTypeList.add(ouvrageType);
        }

        response.getOuvrageType().addAll(ouvrageTypeList);
        return response;
    }


    /**
     * Cette méthode ajoute un ouvrage
     * @param request
     * @return
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addOuvrageRequest")
    @ResponsePayload
    public AddOuvrageResponse addOuvrage(@RequestPayload AddOuvrageRequest request){
        AddOuvrageResponse response = new AddOuvrageResponse();
        OuvrageType newOuvrageType = new OuvrageType();
        ServiceStatus serviceStatus = new ServiceStatus();
        OuvrageEntity newOuvrageEntity = new OuvrageEntity();

        BeanUtils.copyProperties(request.getOuvrageType(), newOuvrageEntity);
        OuvrageEntity savedOuvrageEntity = service.addOuvrage(newOuvrageEntity);

        if(savedOuvrageEntity == null){
            serviceStatus.setStatusCode("CONFLICT");
            serviceStatus.setMessage("Exception while adding Entity");
        } else {

            BeanUtils.copyProperties(savedOuvrageEntity, newOuvrageType);
            serviceStatus.setStatusCode("SUCCESS");
            serviceStatus.setMessage("Content Added Successfully");
        }

        response.setOuvrageType(newOuvrageType);
        response.setServiceStatus(serviceStatus);
        return response;
    }


    /**
     * Cette méthode met à jour un ouvrage
     * @param request
     * @return
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateOuvrageRequest")
    @ResponsePayload
    public UpdateOuvrageResponse updateOuvrage(@RequestPayload UpdateOuvrageRequest request){
        UpdateOuvrageResponse response = new UpdateOuvrageResponse();
        ServiceStatus serviceStatus = new ServiceStatus();

        // 1. Trouver si le ouvrage est disponible
        OuvrageEntity upOuvrage = service.getOuvrageById(request.getOuvrageType().getId());

        if(upOuvrage == null){
            serviceStatus.setStatusCode("NOT FOUND");
            serviceStatus.setMessage("Ouvrage : " + request.getOuvrageType().getTitre() + " " +
                    request.getOuvrageType().getRef() + " " + " not found");
        } else {

            // 2. Obtenir les informations du compte à mettre à jour à partir de la requête
            BeanUtils.copyProperties(request.getOuvrageType(), upOuvrage);

            // 3. Mettre à jour le compte dans la base de données
            boolean flag = service.updateOuvrage(upOuvrage);

            if(flag == false){
                serviceStatus.setStatusCode("CONFLICT");
                serviceStatus.setMessage("Exception while updating Entity : " + request.getOuvrageType().getTitre() + " " +
                        request.getOuvrageType().getRef());
            } else {
                serviceStatus.setStatusCode("SUCCESS");
                serviceStatus.setMessage("Content updated Successfully");
            }
        }

        response.setServiceStatus(serviceStatus);
        return response;
    }


    /**
     * Cette méthode suppprime un ouvrage
     * @param request
     * @return
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteOuvrageRequest")
    @ResponsePayload
    public DeleteOuvrageResponse deleteOuvrage(@RequestPayload DeleteOuvrageRequest request){
        DeleteOuvrageResponse response = new DeleteOuvrageResponse();
        ServiceStatus serviceStatus = new ServiceStatus();

        boolean flag = service.deleteOuvrage(request.getOuvrageId());

        if(flag == false){
            serviceStatus.setStatusCode("FAIL");
            serviceStatus.setMessage("Exception while deletint Entity id : " + request.getOuvrageId());
        } else {
            serviceStatus.setStatusCode("SUCCESS");
            serviceStatus.setMessage("Content Deleted Successfully");
        }

        response.setServiceStatus(serviceStatus);
        return response;
    }


    /**
     * Cette méthode récupère un ou des ouvrages par mot-clé transmis par l'utilisateur
     * @param request
     * @return
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getSearchByKeywordRequest")
    @ResponsePayload
    @Transactional
    public GetSearchByKeywordResponse getSearchByKeyword(@RequestPayload GetSearchByKeywordRequest request){
        GetSearchByKeywordResponse response = new GetSearchByKeywordResponse();
        List<OuvrageEntity> ouvrageEntityList = service.getAllOuvagesByKeyword("%" + request.getKeyword() + "%");
        List<OuvrageType> ouvrageTypeList = new ArrayList<>();

        for (OuvrageEntity entity : ouvrageEntityList){
            OuvrageType ouvrageType = new OuvrageType();

            for (LivreEntity livreEntity : entity.getLivres()){
                LivreType livreType = new LivreType();

                BeanUtils.copyProperties(livreEntity, livreType);
                ouvrageType.getLivres().add(livreType);
            }

            for (PhotoEntity photoEntity : entity.getPhotos()){
                PhotoType photoType = new PhotoType();

                BeanUtils.copyProperties(photoEntity, photoType);
                ouvrageType.getPhotos().add(photoType);
            }

            BeanUtils.copyProperties(entity, ouvrageType);
            ouvrageTypeList.add(ouvrageType);
        }

        response.getOuvrageType().addAll(ouvrageTypeList);
        return response;
    }
}















