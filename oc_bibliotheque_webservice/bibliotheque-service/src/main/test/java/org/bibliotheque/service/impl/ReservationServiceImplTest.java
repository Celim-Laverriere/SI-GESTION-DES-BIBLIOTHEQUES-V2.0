package org.bibliotheque.service.impl;

import org.bibliotheque.entity.ReservationEntity;
import org.bibliotheque.repository.ReservationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ReservationServiceImplTest {

    @Mock
    ReservationRepository reservationRepository;

    @InjectMocks
    ReservationServiceImpl reservationServiceImpl;

    @BeforeEach
    void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getReservationById() throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        ReservationEntity reservationMock = ReservationEntity.builder().id(1)
                .dateResaDisponible(dateFormat.parse("2020-01-08")).numPositionResa(1).ouvrageId(1).compteId(2)
                .build();

        when(reservationRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(reservationMock));
        final ReservationEntity reservationEntity = reservationServiceImpl.getReservationById(1);
        Assertions.assertEquals(reservationMock.getStatut(), reservationEntity.getStatut());
        Assertions.assertEquals(reservationMock.getCompteId(), reservationEntity.getCompteId());
    }

    @Test
    void getAllReservations() throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        List<ReservationEntity> reservationMockList = new ArrayList<>();

        ReservationEntity reservationMock1 = ReservationEntity.builder().id(1)
                .dateResaDisponible(dateFormat.parse("2020-01-08")).numPositionResa(1).ouvrageId(1).compteId(2)
                .build();
        reservationMockList.add(reservationMock1);

        ReservationEntity reservationMock2 = ReservationEntity.builder().id(1)
                .dateResaDisponible(dateFormat.parse("2020-01-08")).numPositionResa(2).ouvrageId(1).compteId(4)
                .build();
        reservationMockList.add(reservationMock2);

        ReservationEntity reservationMock3 = ReservationEntity.builder().id(1)
                .dateResaDisponible(dateFormat.parse("2020-01-08")).numPositionResa(3).ouvrageId(1).compteId(1)
                .build();
        reservationMockList.add(reservationMock3);

        when(reservationRepository.findAll()).thenReturn(reservationMockList);
        final List<ReservationEntity> reservationEntityList = reservationServiceImpl.getAllReservations();
        Assertions.assertEquals(reservationEntityList.size(), 3);
        Assertions.assertEquals(reservationEntityList.get(1).getCompteId(), reservationMock2.getCompteId());
    }

    @Test
    void getListReservationByOuvrageId() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        List<ReservationEntity> reservationMockList = new ArrayList<>();

        ReservationEntity reservationMock1 = ReservationEntity.builder().id(1)
                .dateResaDisponible(dateFormat.parse("2020-01-08")).numPositionResa(1).ouvrageId(1).compteId(2)
                .build();
        reservationMockList.add(reservationMock1);

        ReservationEntity reservationMock2 = ReservationEntity.builder().id(1)
                .dateResaDisponible(dateFormat.parse("2020-01-08")).numPositionResa(2).ouvrageId(1).compteId(4)
                .build();
        reservationMockList.add(reservationMock2);

        ReservationEntity reservationMock3 = ReservationEntity.builder().id(1)
                .dateResaDisponible(dateFormat.parse("2020-01-08")).numPositionResa(3).ouvrageId(1).compteId(1)
                .build();
        reservationMockList.add(reservationMock3);

        when(reservationRepository.findAllByOuvrageId(1)).thenReturn(reservationMockList);
        final List<ReservationEntity> reservationEntityList = reservationServiceImpl.getListReservationByOuvrageId(1);
        Assertions.assertEquals(reservationEntityList.size(), 3);

    }

    @Test
    void getListReservationByCompteId() throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        List<ReservationEntity> reservationMockList = new ArrayList<>();

        ReservationEntity reservationMock1 = ReservationEntity.builder().id(1)
                .dateResaDisponible(dateFormat.parse("2020-01-08")).numPositionResa(1).ouvrageId(1).compteId(2)
                .build();
        reservationMockList.add(reservationMock1);

        ReservationEntity reservationMock2 = ReservationEntity.builder().id(1)
                .dateResaDisponible(dateFormat.parse("2020-01-08")).numPositionResa(1).ouvrageId(2).compteId(2)
                .build();
        reservationMockList.add(reservationMock2);

        when(reservationRepository.findAllByCompteId(2)).thenReturn(reservationMockList);
        final List<ReservationEntity> reservationEntityList = reservationServiceImpl.getListReservationByCompteId(2);
        Assertions.assertEquals(reservationEntityList.size(), 2);
        Assertions.assertEquals(reservationEntityList.get(1).getCompteId(), reservationMock2.getCompteId());
    }
}