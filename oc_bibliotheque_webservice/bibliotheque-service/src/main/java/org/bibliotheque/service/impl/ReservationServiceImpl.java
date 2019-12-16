package org.bibliotheque.service.impl;

import org.bibliotheque.entity.ReservationEntity;
import org.bibliotheque.repository.ReservationRepository;
import org.bibliotheque.service.contract.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

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
    public boolean deleteReservaion(Integer id) {
        try {
            this.reservationRepository.deleteById(id);
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
    public ReservationEntity addReservation(ReservationEntity reservationEntity) {
        try {
            return this.reservationRepository.save(reservationEntity);
        } catch (Exception pEX) {
            pEX.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean updateReservation(ReservationEntity reservationEntity) {
        try{
            this.reservationRepository.save(reservationEntity);
            return true;
        } catch (Exception pEX) {
            pEX.printStackTrace();
            return false;
        }
    }
}
