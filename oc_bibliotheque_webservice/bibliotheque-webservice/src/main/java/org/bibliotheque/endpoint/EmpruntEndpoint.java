package org.bibliotheque.endpoint;

import com.bibliotheque.gs_ws.*;
import lombok.NoArgsConstructor;
import org.bibliotheque.entity.EmpruntEntity;
import org.bibliotheque.service.contract.EmpruntService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Endpoint
@NoArgsConstructor
public class EmpruntEndpoint  {

    public static final String NAMESPACE_URI = "http://www.webservice.org/bibliotheque-ws";

    @Autowired
    private EmpruntService service;


    /**
     * Cette méthode récupère emprunt par son identifiant
     * @param request
     * @return Un emprunt
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getEmpruntByIdRequest")
    @ResponsePayload
    public GetEmpruntByIdResponse getEmpruntById(@RequestPayload GetEmpruntByIdRequest request)
            throws DatatypeConfigurationException {
        GetEmpruntByIdResponse response = new GetEmpruntByIdResponse();
        EmpruntEntity empruntEntity = service.getEmpruntById(request.getEmpruntId());
        EmpruntType empruntType = new EmpruntType();

        GregorianCalendar calendar = new GregorianCalendar();

        calendar.setTime(empruntEntity.getDateDebut());
        XMLGregorianCalendar dateDebut = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
        empruntType.setDateDebut(dateDebut);

        calendar.setTime(empruntEntity.getDateFin());
        XMLGregorianCalendar dateFin = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
        empruntType.setDateFin(dateFin);

        BeanUtils.copyProperties(empruntEntity, empruntType);
        response.setEmpruntType(empruntType);
        return response;
    }


    /**
     * Cette méthode récupère tous emprunts
     * @param request
     * @return Liste d'emprunts
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllEmpruntRequest")
    @ResponsePayload
    public GetAllEmpruntResponse getAllEmprunt(@RequestPayload GetAllEmpruntRequest request)
            throws DatatypeConfigurationException {
        GetAllEmpruntResponse response = new GetAllEmpruntResponse();
        List<EmpruntType> empruntTypeList = new ArrayList<>();
        List<EmpruntEntity> empruntEntityList = service.getAllEmprunts();

        for (EmpruntEntity entity : empruntEntityList){
            EmpruntType empruntType = new EmpruntType();
            GregorianCalendar calendar = new GregorianCalendar();

            calendar.setTime(entity.getDateDebut());
            XMLGregorianCalendar dateDebut = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);

            calendar.setTime(entity.getDateFin());
            XMLGregorianCalendar dateFin = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);

            empruntType.setId(entity.getId());
            empruntType.setDateDebut(dateDebut);
            empruntType.setDateFin(dateFin);
            empruntType.setStatut(entity.getStatut());
            empruntType.setProlongation(entity.getProlongation());
            empruntType.setCompteId(entity.getCompteId());
            empruntType.setLivreId(entity.getLivreId());

            empruntTypeList.add(empruntType);
        }

        response.getEmpruntType().addAll(empruntTypeList);
        return response;
    }


    /**
     * Cette méthode récupère tous les emprunts liés à un compte client
     * @param request id
     * @return Liste d'emprunts
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllEmpruntByCompteIdRequest")
    @ResponsePayload
    public GetAllEmpruntByCompteIdResponse getAllEmpruntByCompteId(@RequestPayload GetAllEmpruntByCompteIdRequest request)
            throws DatatypeConfigurationException, ParseException {
        GetAllEmpruntByCompteIdResponse response = new GetAllEmpruntByCompteIdResponse();
        ServiceStatus serviceStatus = new ServiceStatus();
        List<EmpruntType> empruntTypeList = new ArrayList<>();

        List<EmpruntEntity> empruntEntityList = service.getAllEmpruntByCompteId(request.getId());

        if (!empruntEntityList.isEmpty()) {

            for (EmpruntEntity entity : empruntEntityList){
                EmpruntType empruntType = new EmpruntType();
                GregorianCalendar calendar = new GregorianCalendar();

                calendar.setTime(entity.getDateDebut());
                XMLGregorianCalendar dateDebut = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);

                calendar.setTime(entity.getDateFin());
                XMLGregorianCalendar dateFin = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);

                empruntType.setId(entity.getId());
                empruntType.setDateDebut(dateDebut);
                empruntType.setDateFin(dateFin);
                empruntType.setStatut(entity.getStatut());
                empruntType.setProlongation(entity.getProlongation());
                empruntType.setCompteId(entity.getCompteId());
                empruntType.setLivreId(entity.getLivreId());

                empruntTypeList.add(empruntType);
            }

        } else {
            serviceStatus.setStatusCode("NOT FOUND");
            serviceStatus.setMessage("Emprunt pour le compte id " + request.getId() + " : not found");
        }

        response.getEmpruntType().addAll(empruntTypeList);
        response.setServiceStatus(serviceStatus);
        return response;
    }


    /**
     * Cette méthode ajoute un emprunt
     * @param request
     * @return
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addEmpruntRequest")
    @ResponsePayload
    public AddEmpruntResponse addEmprunt(@RequestPayload AddEmpruntRequest request) throws ParseException {
        AddEmpruntResponse response = new AddEmpruntResponse();
        EmpruntType newEmpruntType = new EmpruntType();
        EmpruntEntity newEmpruntEntity = new EmpruntEntity();
        ServiceStatus serviceStatus = new ServiceStatus();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        BeanUtils.copyProperties(request.getEmpruntType(), newEmpruntEntity);
        newEmpruntEntity.setDateDebut(dateFormat.parse(request.getEmpruntType().getDateDebut().toString()));
        newEmpruntEntity.setDateFin(dateFormat.parse(request.getEmpruntType().getDateFin().toString()));

        try {

            EmpruntEntity savedEmpruntEntity = service.addEmprunt(newEmpruntEntity);
            BeanUtils.copyProperties(savedEmpruntEntity, newEmpruntType);

            serviceStatus.setStatusCode("SUCCESS");
            serviceStatus.setMessage("Content Added Successfully");

        } catch (DataIntegrityViolationException pEX) {
            serviceStatus.setStatusCode("CONFLICT");
            serviceStatus.setMessage("Exception while adding Entity");
        } catch (Exception pEX) {
            pEX.printStackTrace();
        }

        response.setEmpruntType(newEmpruntType);
        response.setServiceStatus(serviceStatus);
        return response;
    }


    /**
     * Cette méthode met à jour un emprunt
     * @param request
     * @return
     * @throws ParseException
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateEmpruntRequest")
    @ResponsePayload
    public UpdateEmpruntResponse updateEmprunt(@RequestPayload UpdateEmpruntRequest request) throws ParseException {
        UpdateEmpruntResponse response = new UpdateEmpruntResponse();
        ServiceStatus serviceStatus = new ServiceStatus();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date toDay = new Date();

        try{
            // 1. Trouver si l'emprunt est disponible
            EmpruntEntity upEmprunt = service.getEmpruntById(request.getEmpruntType().getId());

            // Vérifie si la date de fin d'emprunt n'est pas passée
            if (upEmprunt.getDateFin().after(toDay)) {

                // 2. Obtenir les informations de l'emprunt à mettre à jour à partir de la requête
                Date dateDebut = dateFormat.parse(request.getEmpruntType().getDateDebut().toString());
                upEmprunt.setDateDebut(dateDebut);

                Date dateFin = dateFormat.parse(request.getEmpruntType().getDateFin().toString());
                upEmprunt.setDateFin(dateFin);

                BeanUtils.copyProperties(request.getEmpruntType(), upEmprunt);

                try {
                    // 3. Mettre à jour l'emprunt dans la base de données
                    service.updateEmprunt(upEmprunt);

                    serviceStatus.setStatusCode("SUCCESS");
                    serviceStatus.setMessage("Content updated Successfully");

                } catch (DataIntegrityViolationException pEX) {
                    serviceStatus.setStatusCode("CONFLICT");
                    serviceStatus.setMessage("Exception while updating Entity : " + request.getEmpruntType().getId());
                } catch (Exception pEX) {
                    pEX.printStackTrace();
                }

            } else {
                serviceStatus.setStatusCode("DATE-EXPIRED");
                serviceStatus.setMessage("La date de fin d'emprunt est dépassée, vous ne pouvez pas prolonger l'emprunt");
            }

        } catch (NoSuchElementException pEX) {
            serviceStatus.setStatusCode("NOT FOUND");
            serviceStatus.setMessage("Emprunt " + request.getEmpruntType().getId() + " : not found");
        } catch (Exception pEX) {
            pEX.printStackTrace();
        }

        response.setServiceStatus(serviceStatus);
        return response;
    }


    /**
     * Cette méthode supprime un emprunt
     * @param request
     * @return
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteEmpruntRequest")
    @ResponsePayload
    public DeleteEmpruntResponse deleteEmprunt(@RequestPayload DeleteEmpruntRequest request){
        DeleteEmpruntResponse response = new DeleteEmpruntResponse();
        ServiceStatus serviceStatus = new ServiceStatus();

        try {
            service.deleteEmprunt(request.getEmpruntId());

            serviceStatus.setStatusCode("SUCCESS");
            serviceStatus.setMessage("Content Deleted Successfully");

        } catch (EmptyResultDataAccessException pEX) {
            serviceStatus.setStatusCode("FAIL");
            serviceStatus.setMessage("Exception while deletint Entity id : " + request.getEmpruntId());
        } catch (Exception pEX) {
            pEX.printStackTrace();
        }

        response.setServiceStatus(serviceStatus);
        return response;
    }

    /**
     * Cette méthode récupère tous les emprunts liés aux livres, eux-mêmes à un ouvrage
     * @param request
     * @return
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllEmpruntByOuvrageIdRequest")
    @ResponsePayload
    public GetAllEmpruntByOuvrageIdResponse getAllEmpruntByOuvrageId(@RequestPayload GetAllEmpruntByOuvrageIdRequest request)
            throws DatatypeConfigurationException {
        GetAllEmpruntByOuvrageIdResponse response = new GetAllEmpruntByOuvrageIdResponse();
        List<EmpruntType> empruntTypeList = new ArrayList<>();
        ServiceStatus serviceStatus = new ServiceStatus();

        List<EmpruntEntity> empruntEntityList = service.getAllEmpruntByOuvrageId(request.getOuvrageId());

        if (!empruntEntityList.isEmpty()) {

            for (EmpruntEntity entity : empruntEntityList ) {
                EmpruntType empruntType = new EmpruntType();
                GregorianCalendar calendar = new GregorianCalendar();

                calendar.setTime(entity.getDateDebut());
                XMLGregorianCalendar dateDebut = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);

                calendar.setTime(entity.getDateFin());
                XMLGregorianCalendar dateFin = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);

                empruntType.setId(entity.getId());
                empruntType.setDateDebut(dateDebut);
                empruntType.setDateFin(dateFin);
                empruntType.setStatut(entity.getStatut());
                empruntType.setProlongation(entity.getProlongation());
                empruntType.setCompteId(entity.getCompteId());
                empruntType.setLivreId(entity.getLivreId());

                empruntTypeList.add(empruntType);
            }
        } else {
            serviceStatus.setStatusCode("NOT FOUND");
            serviceStatus.setMessage("Emprunt " + request.getOuvrageId() + " : not found");
        }

        response.getEmpruntType().addAll(empruntTypeList);
        response.setServiceStatus(serviceStatus);
        return response;
    }
}
