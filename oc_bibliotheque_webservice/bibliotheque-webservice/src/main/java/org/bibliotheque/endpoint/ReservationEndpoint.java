package org.bibliotheque.endpoint;

import com.bibliotheque.gs_ws.*;
import lombok.NoArgsConstructor;
import org.bibliotheque.entity.ReservationEntity;
import org.bibliotheque.service.contract.EmpruntService;
import org.bibliotheque.service.contract.ReservationService;
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
public class ReservationEndpoint {

    public static final String NAMESPACE_URI = "http://www.webservice.org/bibliotheque-ws";

    @Autowired
    private ReservationService service;


    /**
     * Cette méthode récupère tous les réservations
     * @param request
     * @return
     * @throws DatatypeConfigurationException
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllReservationRequest")
    @ResponsePayload
    public GetAllReservationResponse getAllReservation(
            @RequestPayload GetAllReservationRequest request) throws DatatypeConfigurationException {

        GetAllReservationResponse response = new GetAllReservationResponse();
        List<ReservationEntity> reservationEntityList = service.getAllReservations();
        List<ReservationType> reservationTypeList = new ArrayList<>();

        for (ReservationEntity entity : reservationEntityList) {
            ReservationType reservationType = new ReservationType();
            GregorianCalendar calendar = new GregorianCalendar();

            calendar.setTime(entity.getDateDemandeDeResa());
            XMLGregorianCalendar dateDemandeDeResa = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);

            reservationType.setId(entity.getId());
            reservationType.setDateDemandeDeResa(dateDemandeDeResa);
            reservationType.setOuvrageId(entity.getOuvrageId());
            reservationType.setNumPositionResa(entity.getNumPositionResa());
            reservationType.setStatut(entity.getStatut());
            reservationType.setCompteId(entity.getCompteId());

            reservationTypeList.add(reservationType);
        }

        response.getReservationList().addAll(reservationTypeList);
        return response;
    }


    /**
     * Cette méthode récupère la liste des réservations liée à un ouvrage
     * @param request
     * @return
     * @throws DatatypeConfigurationException
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getListReservationByOuvrageIdRequest")
    @ResponsePayload
    public GetListReservationByOuvrageIdResponse getListReservationByOuvrageId(
            @RequestPayload GetListReservationByOuvrageIdRequest request) throws DatatypeConfigurationException {
        GetListReservationByOuvrageIdResponse response = new GetListReservationByOuvrageIdResponse();
        List<ReservationEntity> reservationEntityList = service.getListReservationByOuvrageId(request.getOuvrageId());

        List<ReservationType> reservationTypeList = new ArrayList<>();

        for (ReservationEntity entity : reservationEntityList) {
            ReservationType reservationType = new ReservationType();
            GregorianCalendar calendar = new GregorianCalendar();

            calendar.setTime(entity.getDateDemandeDeResa());
            XMLGregorianCalendar dateDemandeDeResa = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);

            reservationType.setId(entity.getId());
            reservationType.setDateDemandeDeResa(dateDemandeDeResa);
            reservationType.setOuvrageId(entity.getOuvrageId());
            reservationType.setNumPositionResa(entity.getNumPositionResa());
            reservationType.setStatut(entity.getStatut());
            reservationType.setCompteId(entity.getCompteId());

            reservationTypeList.add(reservationType);
        }

        response.getReservationListByOuvrageId().addAll(reservationTypeList);
        return response;
    }


    /**
     * Cette méthode récupère tous les réservations liées à un compte client
     * @param request
     * @return
     * @throws DatatypeConfigurationException
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getListReservationByCompteIdRequest")
    @ResponsePayload
    public GetListReservationByCompteIdResponse getListReservationByCompteId(
            @RequestPayload GetListReservationByCompteIdRequest request) throws DatatypeConfigurationException {
        GetListReservationByCompteIdResponse response = new GetListReservationByCompteIdResponse();
        List<ReservationEntity> reservationEntityList = service.getListReservationByCompteId(request.getCompteId());
        List<ReservationType> reservationTypeList = new ArrayList<>();

        for (ReservationEntity entity : reservationEntityList) {
            ReservationType reservationType = new ReservationType();
            GregorianCalendar calendar = new GregorianCalendar();

            calendar.setTime(entity.getDateDemandeDeResa());
            XMLGregorianCalendar dateDemandeDeResa = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);

            reservationType.setId(entity.getId());
            reservationType.setDateDemandeDeResa(dateDemandeDeResa);
            reservationType.setOuvrageId(entity.getOuvrageId());
            reservationType.setNumPositionResa(entity.getNumPositionResa());
            reservationType.setStatut(entity.getStatut());
            reservationType.setCompteId(entity.getCompteId());

            reservationTypeList.add(reservationType);
        }

        response.getReservationListByCompteId().addAll(reservationTypeList);
        return response;
    }


    /**
     * Cette méthode supprime une réservation
     * @param request
     * @return
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteReservationRequest")
    @ResponsePayload
    public DeleteReservationResponse deleteReservation(@RequestPayload DeleteReservationRequest request){
        DeleteReservationResponse response = new DeleteReservationResponse();
        ServiceStatus serviceStatus = new ServiceStatus();

        boolean flag = service.deleteReservation(request.getReservationId());

        if (flag == false) {
            serviceStatus.setStatusCode("FAIL");
            serviceStatus.setMessage("Exception while deletint Entity id : " + request.getReservationId());
        } else {
            serviceStatus.setStatusCode("SUCCESS");
            serviceStatus.setMessage("Content Deleted Successfully");
        }

        response.setServiceStatus(serviceStatus);
        return response;
    }


    /**
     * Cette méthode ajoute une réservation
     * @param request
     * @return
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addReservationRequest")
    @ResponsePayload
    public AddReservationResponse addReservationResponse(@RequestPayload  AddReservationRequest request) throws DatatypeConfigurationException {
        AddReservationResponse response = new AddReservationResponse();
        ReservationType reservationType = new ReservationType();
        ReservationEntity reservationEntity = new ReservationEntity();
        ServiceStatus serviceStatus = new ServiceStatus();

        BeanUtils.copyProperties(request.getReservationType(), reservationEntity);
        reservationEntity.setStatut("En cours");
        ReservationEntity savedReservationEntity = service.addReservation(reservationEntity);

        if (savedReservationEntity == null) {
            serviceStatus.setStatusCode("CONFLICT");
            serviceStatus.setMessage("Exception while adding Entity");
        } else {
            BeanUtils.copyProperties(savedReservationEntity, reservationType);
            serviceStatus.setStatusCode("SUCCESS");
            serviceStatus.setMessage("Content Added Successfully");
        }

        response.setReservationType(reservationType);
        response.setServiceStatus(serviceStatus);
        return response;
    }


    /**
     * Cette méthode met à jour une réservation
     * @param request
     * @return
     * @throws ParseException
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateReservationRequest")
    @ResponsePayload
    public UpdateReservationResponse updateReservation (@RequestPayload UpdateReservationRequest request) throws ParseException {
        UpdateReservationResponse response = new UpdateReservationResponse();
        ReservationType reservationType = new ReservationType();
        ServiceStatus serviceStatus = new ServiceStatus();

        // 1. Trouver si la réservation est disponible
        ReservationEntity reservationEntity = service.getReservationById(request.getReservationType().getId());

        if (reservationEntity == null){
            serviceStatus.setStatusCode("NOT FOUND");
            serviceStatus.setMessage("Réservation : " + request.getReservationType().getId() + " not found");
        } else {
            // 2. Obtenir les informations de la réservation à mettre à jour à partir de la requête
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            Date dateDeResa = dateFormat.parse(request.getReservationType().getDateDemandeDeResa().toString());
            reservationEntity.setDateDemandeDeResa(dateDeResa);

            BeanUtils.copyProperties(request.getReservationType(), reservationEntity);

            // 3. Mettre à jour l'emprunt dans la base de données
            boolean flag = service.updateReservation(reservationEntity);

            if(flag == false) {
                serviceStatus.setStatusCode("CONFLICT");
                serviceStatus.setMessage("Exception while updating Entity : " + request.getReservationType().getId());
            } else {
                serviceStatus.setStatusCode("SUCCESS");
                serviceStatus.setMessage("Content updated Successfully");
                reservationEntity = service.getReservationById(request.getReservationType().getId());
                BeanUtils.copyProperties(reservationEntity, reservationType);
            }
        }

        response.setReservationType(reservationType);
        response.setServiceStatus(serviceStatus);
        return response;
    }

}
