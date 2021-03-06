package org.bibliotheque.service.contract;

import org.bibliotheque.entity.ReservationEntity;

import javax.xml.datatype.DatatypeConfigurationException;
import java.util.List;

public interface ReservationService {

    ReservationEntity getReservationById(Integer id);

    List<ReservationEntity> getAllReservations();

    List<ReservationEntity> getListReservationByOuvrageId(Integer ouvrageId);

    List<ReservationEntity> getListReservationByCompteId(Integer compteId);

    void deleteReservation(Integer id);

    ReservationEntity addReservation(ReservationEntity reservationEntity) throws DatatypeConfigurationException;

    void updateReservation(ReservationEntity reservationEntity);
}