package org.bibliotheque.service.impl;

import org.bibliotheque.entity.ReservationEntity;
import org.bibliotheque.repository.ReservationRepository;
import org.bibliotheque.service.contract.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.*;

@Service
@Transactional
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public ReservationEntity getReservationById(Integer id) {
        return this.reservationRepository.findById(id).get();
    }

    @Override
    public List<ReservationEntity> getAllReservations() {
        List<ReservationEntity> reservationEntityList = new ArrayList<>();
        this.reservationRepository.findAll().forEach(e -> reservationEntityList.add(e));
        return reservationEntityList;
    }

    @Override
    public List<ReservationEntity> getListReservationByOuvrageId(Integer ouvrageId) {
        List<ReservationEntity> reservationEntities = new ArrayList<>();
        this.reservationRepository.findAllByOuvrageId(ouvrageId).forEach(e -> reservationEntities.add(e));
        return reservationEntities;
    }

    @Override
    public boolean deleteReservation(Integer reservationId) {

        ReservationEntity reservationEntity = getReservationById(reservationId);
        List<ReservationEntity> reservationEntityList = reservationRepository.findAllByOuvrageId(reservationEntity.getOuvrageId());

        for (ReservationEntity entity : reservationEntityList){

            if (reservationEntity.getNumPositionResa() < entity.getNumPositionResa()) {
                entity.setNumPositionResa(entity.getNumPositionResa() - 1);
                updateReservation(entity);
            }
        }

        try {
            this.reservationRepository.deleteById(reservationId);
            return true;
        } catch (Exception pEX) {
            pEX.getMessage();
            return false;
        }
    }

    @Override
    public List<ReservationEntity> getListReservationByCompteId(Integer compteId) {
        List<ReservationEntity> reservationEntities = new ArrayList<>();
        this.reservationRepository.findAllByCompteId(compteId).forEach(e -> reservationEntities.add(e));
        return reservationEntities;
    }

    @Override
    public ReservationEntity addReservation(ReservationEntity reservationEntity) throws DatatypeConfigurationException {

        List<Integer> positionResaList = new ArrayList<>();
        List<ReservationEntity> reservationEntityList = getListReservationByOuvrageId(reservationEntity.getOuvrageId());

        Date dateToDay = new Date();
        reservationEntity.setDateDemandeDeResa(dateToDay);

        for (ReservationEntity entity : reservationEntityList){
            positionResaList.add(entity.getNumPositionResa());
            Collections.sort(positionResaList);
        }

        if (positionResaList.size() > 0) {
            reservationEntity.setNumPositionResa(positionResaList.get(positionResaList.size() - 1) + 1);
        } else {
            reservationEntity.setNumPositionResa(1);
        }

        try {
            return this.reservationRepository.save(reservationEntity);
        } catch (Exception pEX) {
            pEX.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean updateReservation(ReservationEntity reservationEntity) {

        List<ReservationEntity> reservationEntityList = reservationRepository.findAllByOuvrageId(reservationEntity.getOuvrageId());

        for (ReservationEntity entity : reservationEntityList){

            if (reservationEntity.getNumPositionResa() < entity.getNumPositionResa() && reservationEntity.getNumPositionResa() != 0) {
                entity.setNumPositionResa(entity.getNumPositionResa() - 1);
                updateReservation(entity);
                reservationEntity.setNumPositionResa(0);
            }
        }

        try{
            this.reservationRepository.save(reservationEntity);
            return true;
        } catch (Exception pEX) {
            pEX.printStackTrace();
            return false;
        }
    }
}
