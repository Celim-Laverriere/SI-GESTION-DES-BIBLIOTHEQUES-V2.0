package org.bibliotheque.endpoint;

import com.bibliotheque.gs_ws.*;
import lombok.NoArgsConstructor;
import org.bibliotheque.entity.EmpruntEntity;
import org.bibliotheque.service.contract.EmpruntService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

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
    public GetEmpruntByIdResponse getEmpruntById(@RequestPayload GetEmpruntByIdRequest request) throws DatatypeConfigurationException {
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
    public GetAllEmpruntResponse getAllEmprunt(@RequestPayload GetAllEmpruntRequest request) throws DatatypeConfigurationException {
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
    public GetAllEmpruntByCompteIdResponse getAllEmpruntByCompteId(@RequestPayload GetAllEmpruntByCompteIdRequest request) throws DatatypeConfigurationException, ParseException {
        GetAllEmpruntByCompteIdResponse response = new GetAllEmpruntByCompteIdResponse();
        List<EmpruntEntity> empruntEntityList = service.getAllEmpruntByCompteId(request.getId());
        List<EmpruntType> empruntTypeList = new ArrayList<>();


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
            empruntType.setProlongation(entity.getProlongation());
            empruntType.setCompteId(entity.getCompteId());
            empruntType.setLivreId(entity.getLivreId());

            empruntTypeList.add(empruntType);
        }

        response.getEmpruntType().addAll(empruntTypeList);
        return response;
    }


    /**
     * Cette méthode ajoute un emprunt
     * @param request
     * @return
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addEmpruntRequest")
    @ResponsePayload
    public AddEmpruntResponse addEmprunt(@RequestPayload AddEmpruntRequest request){
        AddEmpruntResponse response = new AddEmpruntResponse();
        EmpruntType newEmpruntType = new EmpruntType();
        EmpruntEntity newEmpruntEntity = new EmpruntEntity();
        ServiceStatus serviceStatus = new ServiceStatus();

        BeanUtils.copyProperties(request.getEmpruntType(), newEmpruntEntity);
        EmpruntEntity savedEmpruntEntity = service.addEmprunt(newEmpruntEntity);

        if(savedEmpruntEntity == null){
            serviceStatus.setStatusCode("CONFLICT");
            serviceStatus.setMessage("Exception while adding Entity");
        } else {

            BeanUtils.copyProperties(savedEmpruntEntity, newEmpruntType);
            serviceStatus.setStatusCode("SUCCESS");
            serviceStatus.setMessage("Content Added Successfully");
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

        // 1. Trouver si l'emprunt est disponible
        EmpruntEntity upEmprunt = service.getEmpruntById(request.getEmpruntType().getId());

        if (upEmprunt == null){
            serviceStatus.setStatusCode("NOT FOUND");
            serviceStatus.setMessage("Emprunt : " + request.getEmpruntType().getId() + " not found");
        } else {

            // 2. Obtenir les informations de l'emprunt à mettre à jour à partir de la requête
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            Date dateDebut = dateFormat.parse(request.getEmpruntType().getDateDebut().toString());
            upEmprunt.setDateDebut(dateDebut);

            Date dateFin = dateFormat.parse(request.getEmpruntType().getDateFin().toString());
            upEmprunt.setDateFin(dateFin);

            BeanUtils.copyProperties(request.getEmpruntType(), upEmprunt);

            // 3. Mettre à jour l'emprunt dans la base de données
            boolean flag = service.updateEmprunt(upEmprunt);

            if(flag == false){
                serviceStatus.setStatusCode("CONFLICT");
                serviceStatus.setMessage("Exception while updating Entity : " + request.getEmpruntType().getId());
            } else {
                serviceStatus.setStatusCode("SUCCESS");
                serviceStatus.setMessage("Content updated Successfully");
            }
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

        boolean flag = service.deleteEmprunt(request.getEmpruntId());

        if (flag == false){
            serviceStatus.setStatusCode("FAIL");
            serviceStatus.setMessage("Exception while deletint Entity id : " + request.getEmpruntId());
        } else {
            serviceStatus.setStatusCode("SUCCESS");
            serviceStatus.setMessage("Content Deleted Successfully");
        }

        response.setServiceStatus(serviceStatus);
        return response;
    }

}
