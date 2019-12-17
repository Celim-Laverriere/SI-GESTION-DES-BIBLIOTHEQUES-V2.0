package org.bibliotheque.client;


import org.bibliotheque.wsdl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.SoapFaultClientException;

public class ReservationClient extends WebServiceGatewaySupport {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(ReservationClient.class);

    /**
     * ==== CETTE METHODE RECUPERER LA LISTE DES RESERVATIONS D'UN OUVRAGE PAR SON IDENTIFIANT ====
     * @param ouvrage_id
     * @return
     */
    public GetListReservationByOuvrageIdResponse getListReservationByOuvrageId (Integer ouvrage_id){

        GetListReservationByOuvrageIdResponse response = new GetListReservationByOuvrageIdResponse();

        try{
            GetListReservationByOuvrageIdRequest request = new GetListReservationByOuvrageIdRequest();
            request.setOuvrageId(ouvrage_id);
            response = (GetListReservationByOuvrageIdResponse) getWebServiceTemplate().
                    marshalSendAndReceive(request);

        } catch (SoapFaultClientException pEX) {
            logger.error("GetListReservationByOuvrageIdResponse : {}", pEX.getMessage());
        }

        return response;
    }


    /**
     * ==== CETTE METHODE RECUPERER LA LISTE DES RESERVATIONS D'UN CLIENT PAR SON IDENTIFIANT ====
     * @param compteId
     * @return
     */
    public GetListReservationByCompteIdResponse getListReservationByCompteId (Integer compteId) {

        GetListReservationByCompteIdResponse response = new GetListReservationByCompteIdResponse();

        try {
            GetListReservationByCompteIdRequest request = new GetListReservationByCompteIdRequest();
            request.setCompteId(compteId);
            response = (GetListReservationByCompteIdResponse) getWebServiceTemplate().marshalSendAndReceive(request);
        } catch (SoapFaultClientException pEX) {
            logger.error("GetListReservationByCompteIdResponse : {}", pEX.getMessage());
        }

        return response;
    }

    /**
     * ==== CETTE METHODE AJOUTE UNE RESERVATION FAITE PAR UN CLIENT ====
     * @param reservationType
     * @return
     */
    public AddReservationResponse addReservation(ReservationType reservationType){
        AddReservationResponse response = new AddReservationResponse();

        try {
            AddReservationRequest request = new AddReservationRequest();
            request.setReservationType(reservationType);
            response = (AddReservationResponse) getWebServiceTemplate().marshalSendAndReceive(request);
        } catch (SoapFaultClientException pEX){
            logger.error("AddReservationResponse : {}", pEX.toString());
        }

        return response;
    }

    /**
     * CETTE METHODE SUPPRIME UNE RESERVATION
     * @param reservationId
     * @return
     */
    public DeleteReservationResponse deleteReservation(Integer reservationId){
        DeleteReservationResponse response = new DeleteReservationResponse();

        try{
            DeleteReservationRequest request = new DeleteReservationRequest();
            request.setReservationId(reservationId);
            response = (DeleteReservationResponse) getWebServiceTemplate().marshalSendAndReceive(request);
        } catch (SoapFaultClientException pEX){
            logger.error("DeleteReservationResponse : {}", pEX.toString());
        }

        return response;
    }


    /**
     * CETTE METHODE MET A JOUR UNE RESERVATION
     * @param reservationType
     * @return
     */
    public UpdateReservationResponse updateReservation(ReservationType reservationType){
        UpdateReservationResponse response = new UpdateReservationResponse();

        try{
            UpdateReservationRequest request = new UpdateReservationRequest();
            request.setReservationType(reservationType);
            response = (UpdateReservationResponse) getWebServiceTemplate().marshalSendAndReceive(request);
        } catch (SoapFaultClientException pEX){
            logger.error("UpdateReservationResponse : {}", pEX.toString());
        }

        return response;
    }



}
