package org.bibliotheque.endpoint;

import com.bibliotheque.gs_ws.*;
import lombok.NoArgsConstructor;
import org.bibliotheque.entity.ReservationEntity;
import org.bibliotheque.service.contract.ReservationService;
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

            calendar.setTime(entity.getDateResaDisponible());
            XMLGregorianCalendar dateDemandeDeResa = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);

            reservationType.setId(entity.getId());
            reservationType.setDateResaDisponible(dateDemandeDeResa);
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

            calendar.setTime(entity.getDateResaDisponible());
            XMLGregorianCalendar dateDemandeDeResa = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);

            reservationType.setId(entity.getId());
            reservationType.setDateResaDisponible(dateDemandeDeResa);
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

            calendar.setTime(entity.getDateResaDisponible());
            XMLGregorianCalendar dateResaDisponible = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);

            reservationType.setId(entity.getId());
            reservationType.setDateResaDisponible(dateResaDisponible);
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

        try {
            service.deleteReservation(request.getReservationId());

            serviceStatus.setStatusCode("SUCCESS");
            serviceStatus.setMessage("Content Deleted Successfully");

        } catch (NoSuchElementException pEX) {
            serviceStatus.setStatusCode("NOT FOUND");
            serviceStatus.setMessage("Réservation " + request.getReservationId() + " : not found");
        } catch(EmptyResultDataAccessException pEX) {
            serviceStatus.setStatusCode("FAIL");
            serviceStatus.setMessage("Exception while deletint Entity id : " + request.getReservationId());
        } catch (Exception pEX) {
            pEX.printStackTrace();
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
    public AddReservationResponse addReservationResponse(@RequestPayload  AddReservationRequest request)
            throws DatatypeConfigurationException, ParseException {
        AddReservationResponse response = new AddReservationResponse();
        ReservationType reservationType = new ReservationType();
        ReservationEntity reservationEntity = new ReservationEntity();
        ServiceStatus serviceStatus = new ServiceStatus();

        BeanUtils.copyProperties(request.getReservationType(), reservationEntity);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        reservationEntity.setDateResaDisponible(dateFormat.parse(request.getReservationType().getDateResaDisponible().toString()));

        try {
            ReservationEntity savedReservationEntity = service.addReservation(reservationEntity);
            BeanUtils.copyProperties(savedReservationEntity, reservationType);

            serviceStatus.setStatusCode("SUCCESS");
            serviceStatus.setMessage("Content Added Successfully");

        } catch (DataIntegrityViolationException pEX) {
            serviceStatus.setStatusCode("CONFLICT");
            serviceStatus.setMessage("Exception while adding Entity");
        } catch (Exception pEX) {
            pEX.printStackTrace();
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
    public UpdateReservationResponse updateReservation (@RequestPayload UpdateReservationRequest request)
            throws ParseException {
        UpdateReservationResponse response = new UpdateReservationResponse();
        ReservationType reservationType = new ReservationType();
        ServiceStatus serviceStatus = new ServiceStatus();

      try {
          // 1. Trouver si la réservation est disponible
          ReservationEntity reservationEntity = service.getReservationById(request.getReservationType().getId());

          // 2. Obtenir les informations de la réservation à mettre à jour à partir de la requête
          SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

          Date dateDeResa = dateFormat.parse(request.getReservationType().getDateResaDisponible().toString());
          reservationEntity.setDateResaDisponible(dateDeResa);

          BeanUtils.copyProperties(request.getReservationType(), reservationEntity);

          try {
              // 3. Mettre à jour l'emprunt dans la base de données
              service.updateReservation(reservationEntity);

              serviceStatus.setStatusCode("SUCCESS");
              serviceStatus.setMessage("Content updated Successfully");

              reservationEntity = service.getReservationById(request.getReservationType().getId());
              BeanUtils.copyProperties(reservationEntity, reservationType);

          } catch (DataIntegrityViolationException pEX) {
              serviceStatus.setStatusCode("CONFLICT");
              serviceStatus.setMessage("Exception while updating Entity : " + request.getReservationType().getId());
          } catch (Exception pEX) {
              pEX.printStackTrace();
          }

      } catch (NoSuchElementException pEX) {
          serviceStatus.setStatusCode("NOT FOUND");
          serviceStatus.setMessage("Réservation : " + request.getReservationType().getId() + " not found");
      } catch (Exception pEX) {
          pEX.printStackTrace();
      }

        response.setReservationType(reservationType);
        response.setServiceStatus(serviceStatus);
        return response;
    }

}
