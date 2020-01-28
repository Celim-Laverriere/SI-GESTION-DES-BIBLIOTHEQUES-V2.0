package org.bibliotheque.endpoint;

import com.bibliotheque.gs_ws.*;
import lombok.NoArgsConstructor;
import org.bibliotheque.entity.PhotoEntity;
import org.bibliotheque.service.contract.PhotoService;
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
public class PhotoEndpoint {

    public static final String NAMESPACE_URI = "http://www.webservice.org/bibliotheque-ws";

    @Autowired
    private PhotoService service;


    /**
     * Cette méthode récupère une photo par son identifiant
     * @param request
     * @return
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getPhotoByIdRequest")
    @ResponsePayload
    public GetPhotoByIdResponse getPhotoById(@RequestPayload GetPhotoByIdRequest request){
        GetPhotoByIdResponse response = new GetPhotoByIdResponse();
        PhotoEntity photoEntity = service.getPhotoById(request.getPhotoId());
        PhotoType photoType = new PhotoType();
        BeanUtils.copyProperties(photoEntity, photoType);
        response.setPhotoType(photoType);
        return response;
    }


    /**
     * Cette méthode récupère toutes les photos
     * @param request
     * @return
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllPhotosRequest")
    @ResponsePayload
    public GetAllPhotosResponse getAllPhotos(@RequestPayload GetAllPhotosRequest request){
        GetAllPhotosResponse response = new GetAllPhotosResponse();
        List<PhotoType> photoTypeList = new ArrayList<>();
        List<PhotoEntity> photoEntityList = service.getAllPhotos();

        for (PhotoEntity entity : photoEntityList){
            PhotoType photoType = new PhotoType();
            BeanUtils.copyProperties(entity, photoType);
            photoTypeList.add(photoType);
        }

        response.getPhotoType().addAll(photoTypeList);
        return response;
    }


    /**
     * Cette méthode ajoute une photo
     * @param request
     * @return
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addPhotoRequest")
    @ResponsePayload
    public AddPhotoResponse addPhoto(@RequestPayload AddPhotoRequest request){
        AddPhotoResponse response = new AddPhotoResponse();
        PhotoType newPhotoType = new PhotoType();
        ServiceStatus serviceStatus = new ServiceStatus();
        PhotoEntity newPhotoEntity = new PhotoEntity();

        BeanUtils.copyProperties(request.getPhotoType(), newPhotoEntity);

        try {
            PhotoEntity savedPhotoEntity = service.addPhoto(newPhotoEntity);
            BeanUtils.copyProperties(savedPhotoEntity, newPhotoType);

            serviceStatus.setStatusCode("SUCCESS");
            serviceStatus.setMessage("Content Added Successfully");

        } catch (DataIntegrityViolationException pEX) {
            serviceStatus.setStatusCode("CONFLICT");
            serviceStatus.setMessage("Exception while adding Entity");
        } catch (Exception pEX) {
            pEX.printStackTrace();
        }

        response.setPhotoType(newPhotoType);
        response.setServiceStatus(serviceStatus);
        return response;
    }


    /**
     * Cette méthode met à jour une photo
     * @param request
     * @return
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updatePhotoRequest")
    @ResponsePayload
    public UpdatePhotoResponse updatePhoto(@RequestPayload UpdatePhotoRequest request){
        UpdatePhotoResponse response = new UpdatePhotoResponse();
        ServiceStatus serviceStatus = new ServiceStatus();

        try {
            // 1. Trouver si la photo est disponible
            PhotoEntity upPhoto = service.getPhotoById(request.getPhotoType().getId());

            // 2. Obtenir les informations de la photo à mettre à jour à partir de la requête
            BeanUtils.copyProperties(request.getPhotoType(), upPhoto);

            try {
                // 3. Mettre à jour la photo dans la base de données
                service.updatePhoto(upPhoto);

                serviceStatus.setStatusCode("SUCCESS");
                serviceStatus.setMessage("Content updated Successfully");

            } catch (DataIntegrityViolationException pEX) {
                serviceStatus.setStatusCode("CONFLICT");
                serviceStatus.setMessage("Exception while updating Entity : " + request.getPhotoType().getNomPhoto());
            } catch (Exception pEX) {
                pEX.printStackTrace();
            }

        } catch (NoSuchElementException pEX) {
            serviceStatus.setStatusCode("NOT FOUND");
            serviceStatus.setMessage("Compte : " + request.getPhotoType().getNomPhoto() + " " + " not found");
        } catch (Exception pEX) {
            pEX.printStackTrace();
        }

        response.setServiceStatus(serviceStatus);
        return response;
    }


    /**
     * Cette méthode suppprime une photo
     * @param request
     * @return
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deletePhotoRequest")
    @ResponsePayload
    public DeletePhotoResponse deletePhoto(@RequestPayload DeletePhotoRequest request){
        DeletePhotoResponse response = new DeletePhotoResponse();
        ServiceStatus serviceStatus = new ServiceStatus();

        try {
            service.deletePhoto(request.getPhotoId());

            serviceStatus.setStatusCode("SUCCESS");
            serviceStatus.setMessage("Content Deleted Successfully");

        } catch (EmptyResultDataAccessException pEX) {
            serviceStatus.setStatusCode("FAIL");
            serviceStatus.setMessage("Exception while deletint Entity id : " + request.getPhotoId());
        } catch ( Exception pEX) {
            pEX.printStackTrace();
        }

        response.setServiceStatus(serviceStatus);
        return response;
    }
}











































