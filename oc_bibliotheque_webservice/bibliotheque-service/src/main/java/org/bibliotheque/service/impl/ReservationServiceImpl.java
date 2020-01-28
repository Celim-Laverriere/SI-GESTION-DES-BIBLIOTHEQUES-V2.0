package org.bibliotheque.service.impl;

import org.bibliotheque.entity.ReservationEntity;
import org.bibliotheque.repository.ReservationRepository;
import org.bibliotheque.service.contract.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import javax.xml.datatype.DatatypeConfigurationException;
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
    public List<ReservationEntity> getListReservationByCompteId(Integer compteId) {
        List<ReservationEntity> reservationEntities = new ArrayList<>();
        this.reservationRepository.findAllByCompteId(compteId).forEach(e -> reservationEntities.add(e));
        return reservationEntities;
    }

    @Override
    public ReservationEntity addReservation(ReservationEntity reservationEntity) throws DatatypeConfigurationException {

        List<Integer> positionResaList = new ArrayList<>();
        List<ReservationEntity> reservationEntityList = getListReservationByOuvrageId(reservationEntity.getOuvrageId());

        for (ReservationEntity entity : reservationEntityList){
            positionResaList.add(entity.getNumPositionResa());
            Collections.sort(positionResaList);
        }

        if (positionResaList.size() > 0) {
            reservationEntity.setNumPositionResa(positionResaList.get(positionResaList.size() - 1) + 1);
        } else {
            reservationEntity.setNumPositionResa(1);
        }

        return this.reservationRepository.save(reservationEntity);
    }

    @Override
    public void updateReservation(ReservationEntity reservationEntity) {
        this.reservationRepository.save(reservationEntity);
    }

    @Override
    public void deleteReservation(Integer reservationId) {

        ReservationEntity reservationEntity = getReservationById(reservationId);
        List<ReservationEntity> reservationEntityList = reservationRepository.findAllByOuvrageId(reservationEntity.getOuvrageId());

        for (ReservationEntity entity : reservationEntityList){

            if (reservationEntity.getNumPositionResa() < entity.getNumPositionResa() && reservationEntity.getNumPositionResa() != 0) {
                entity.setNumPositionResa(entity.getNumPositionResa() - 1);
                updateReservation(entity);
            }
        }

        this.reservationRepository.deleteById(reservationId);
    }
}
