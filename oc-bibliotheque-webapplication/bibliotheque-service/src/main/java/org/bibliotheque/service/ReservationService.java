package org.bibliotheque.service;

import org.bibliotheque.repository.ReservationRepositoy;
import org.bibliotheque.wsdl.OuvrageType;
import org.bibliotheque.wsdl.ReservationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.xml.datatype.DatatypeConfigurationException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepositoy reservationRepositoy;

    /**
     * ==== CETTE METHODE RECUPERER LA LISTE DES RESERVATIONS D'UN OUVRAGE PAR SON IDENTIFIANT ====
     * @param ouvrageId
     * @return
     */
    public List<ReservationType> reservationTypeListByOuvrageId(Integer ouvrageId){
        return reservationRepositoy.reservationTypeListByOuvrageId(ouvrageId);
    }

    /**
     * ==== CETTE METHODE RECUPERER LA LISTE DES RESERVATIONS D'UN CLIENT PAR SON IDENTIFIANT ====
     * @param compteId
     * @return
     */
    public List<ReservationType> reservationTypeListByCompteId(Integer compteId){
        return reservationRepositoy.reservationTypeListByCompteId(compteId);
    }


    /**
     * ==== CETTE METHODE AJOUTE UNE RESERVATION FAITE PAR UN CLIENT ====
     * @param compteId
     * @param ouvrageId
     * @return
     * @throws ParseException
     * @throws DatatypeConfigurationException
     */
    public String addReservation(Integer compteId, Integer ouvrageId) throws ParseException, DatatypeConfigurationException {

        ReservationType reservationType = new ReservationType();
        reservationType.setCompteId(compteId);
        reservationType.setOuvrageId(ouvrageId);

        return reservationRepositoy.addReservation(reservationType);
    }


    /**
     * ==== CETTE METHODE SUPPRIME UNE RESERVATION ====
     * @param reservationId
     * @return
     */
    public String deleteReservation(Integer reservationId){
        return reservationRepositoy.deleteReservation(reservationId);
    }

    /**
     * ==== CETTE METHODE RECUPERER LA LISTE DES RESERVATIONS ====
     * @return
     */
    public List<ReservationType> getAllReservation(){
        return reservationRepositoy.getAllReservation();
    }

    /**
     * ==== CETTE METHODE TRIE POUR CHAQUE OUVRAGE LE NOMBRE DE RESERVATION "EN COURS" ====
     * @param ouvrageTypeList
     * @return
     */
    public List<OuvrageType> listResaEnCours(List<OuvrageType> ouvrageTypeList){
        List<ReservationType> reservationTypeListEnCours = new ArrayList<>();

        for (OuvrageType ouvrageType : ouvrageTypeList) {

            List<ReservationType> reservationTypeList = new ArrayList<>();
            reservationTypeList.addAll(ouvrageType.getReservations());

            if (!ouvrageType.getReservations().isEmpty()){

                for (ReservationType reservationType : reservationTypeList) {

                    if (reservationType.getStatut().equals("En cours")) {
                        reservationTypeListEnCours.add(reservationType);
                    }
                }
                ouvrageType.getReservations().clear();
                ouvrageType.getReservations().addAll(reservationTypeListEnCours);
                reservationTypeListEnCours.clear();
            }

        }

        return ouvrageTypeList;
    }

    public String updateReservation(ReservationType reservationType){
        return reservationRepositoy.updateReservation(reservationType);
    }
}
