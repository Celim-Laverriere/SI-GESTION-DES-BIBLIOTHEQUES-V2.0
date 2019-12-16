package org.bibliotheque.service.contract;

import org.bibliotheque.entity.ReservationEntity;
import java.util.List;

public interface ReservationService {

    ReservationEntity getReservationById(Integer id);

    List<ReservationEntity> getAllReservations();

    List<ReservationEntity> getListReservationByOuvrageId(Integer ouvrageId);

    List<ReservationEntity> getListReservationByCompteId(Integer compteId);

    boolean deleteReservaion(Integer id);

    ReservationEntity addReservation(ReservationEntity reservationEntity);

    boolean updateReservation(ReservationEntity reservationEntity);
}