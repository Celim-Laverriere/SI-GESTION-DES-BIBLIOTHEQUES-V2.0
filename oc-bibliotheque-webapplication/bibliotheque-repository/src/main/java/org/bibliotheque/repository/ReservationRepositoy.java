package org.bibliotheque.repository;

import org.bibliotheque.client.ReservationClient;
import org.bibliotheque.wsdl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReservationRepositoy {

    @Autowired
    private ReservationClient client;

    /**
     * ==== CETTE METHODE RECUPERER LA LISTE DES RESERVATIONS D'UN OUVRAGE PAR SON IDENTIFIANT ====
     * @param ouvrageId
     * @return
     */
    public List<ReservationType> reservationTypeListByOuvrageId (Integer ouvrageId) {
        GetListReservationByOuvrageIdResponse response = client.getListReservationByOuvrageId(ouvrageId);
        return response.getReservationListByOuvrageId();
    }

    /**
     * ==== CETTE METHODE RECUPERER LA LISTE DES RESERVATIONS D'UN CLIENT PAR SON IDENTIFIANT ====
     * @param compteId
     * @return
     */
    public List<ReservationType> reservationTypeListByCompteId(Integer compteId){
        GetListReservationByCompteIdResponse response = client.getListReservationByCompteId(compteId);
        return response.getReservationListByCompteId();
    }

    /**
     * ==== CETTE METHODE AJOUTE UNE RESERVATION FAITE PAR UN CLIENT ====
     * @param reservationType
     * @return
     */
    public String addReservation(ReservationType reservationType){
        AddReservationResponse response = client.addReservation(reservationType);
        return response.getServiceStatus().getStatusCode();
    }

    /**
     * CETTE METHODE SUPPRIME UNE RESERVATION
     * @param reservationId
     * @return
     */
    public String deleteReservation(Integer reservationId){
        DeleteReservationResponse response = client.deleteReservation(reservationId);
        return response.getServiceStatus().getStatusCode();
    }

    /**
     * CETTE METHODE MET A JOUR UNE RESERVATION
     * @param reservationType
     * @return
     */
    public String updateReservation(ReservationType reservationType){
        UpdateReservationResponse response = client.updateReservation(reservationType);
        return response.getServiceStatus().getStatusCode();
    }
}
