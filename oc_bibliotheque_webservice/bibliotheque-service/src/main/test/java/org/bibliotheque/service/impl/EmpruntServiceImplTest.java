package org.bibliotheque.service.impl;

import org.bibliotheque.entity.EmpruntEntity;
import org.bibliotheque.entity.LivreEntity;
import org.bibliotheque.repository.EmpruntRepository;
import org.bibliotheque.repository.LivreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@DisplayName("Testing EmpruntServiceImplTest")
class EmpruntServiceImplTest {

    @Mock
    EmpruntRepository empruntRepository;

    @Mock
    LivreRepository livreRepository;

    @InjectMocks
    EmpruntServiceImpl empruntServiceImpl;

    @BeforeEach
    void setUp() throws  Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getEmpruntById() throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        EmpruntEntity empruntMock = EmpruntEntity.builder().id(1).dateDebut(dateFormat.parse("2019-12-20"))
                .dateFin(dateFormat.parse("2020-01-13")).statut("En cours").prolongation(false).livreId(2)
                .compteId(3).build();

        when(empruntRepository.findById( anyInt() )).thenReturn(Optional.of(empruntMock));
        final EmpruntEntity empruntDto = empruntServiceImpl.getEmpruntById(1);
        Assertions.assertEquals(empruntDto.getDateDebut(), dateFormat.parse("2019-12-20"));
    }

    @Test
    void getAllEmprunts() throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Liste des emprunts
        List<EmpruntEntity> empruntMockList = new ArrayList<>();

        EmpruntEntity empruntEntity1 = EmpruntEntity.builder().id(1).dateDebut(dateFormat.parse("2019-12-20"))
                .dateFin(dateFormat.parse("2020-01-13")) .statut("En cours").prolongation(false).livreId(7)
                .compteId(3).build();
        empruntMockList.add(empruntEntity1);

        EmpruntEntity empruntEntity2 = EmpruntEntity.builder().id(1).dateDebut(dateFormat.parse("2019-01-20"))
                .dateFin(dateFormat.parse("2020-02-23")) .statut("Rendu").prolongation(false).livreId(2)
                .compteId(3).build();
        empruntMockList.add(empruntEntity2);

        when(empruntRepository.findAll()).thenReturn(empruntMockList);
        final List<EmpruntEntity> empruntEntityList = empruntServiceImpl.getAllEmprunts();
        Assertions.assertEquals(empruntMockList.size(), empruntEntityList.size());
    }

    @Test
    void getAllEmpruntByCompteId() throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Liste des emprunts
        List<EmpruntEntity> empruntMockList = new ArrayList<>();

        EmpruntEntity empruntEntity1 = EmpruntEntity.builder().id(1).dateDebut(dateFormat.parse("2019-12-20"))
                .dateFin(dateFormat.parse("2020-01-13")) .statut("En cours").prolongation(false).livreId(7)
                .compteId(3).build();
        empruntMockList.add(empruntEntity1);

        EmpruntEntity empruntEntity2 = EmpruntEntity.builder().id(1).dateDebut(dateFormat.parse("2019-01-20"))
                .dateFin(dateFormat.parse("2020-02-23")) .statut("Rendu").prolongation(false).livreId(2)
                .compteId(3).build();
        empruntMockList.add(empruntEntity2);

        when(empruntRepository.findAllByCompteId(anyInt())).thenReturn(empruntMockList);
        final List<EmpruntEntity> empruntEntityList = empruntServiceImpl.getAllEmpruntByCompteId(1);
        Assertions.assertEquals(empruntMockList.size(), empruntEntityList.size());
        Assertions.assertEquals(empruntMockList.get(0).getId(), empruntEntityList.get(0).getId());

        empruntMockList.clear();

        when(empruntRepository.findAllByCompteId(anyInt())).thenReturn(empruntMockList);
        final  List<EmpruntEntity> empruntEntityList2 = empruntServiceImpl.getAllEmpruntByCompteId(100);
        Assertions.assertTrue(empruntEntityList2.isEmpty());
    }

    @Test
    void getAllEmpruntByOuvrageId() throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Liste des livres
        List<LivreEntity> livreMockList = new ArrayList<>();

        LivreEntity livreEntity1 = LivreEntity.builder().id(7).refBibliotheque("TIFRHARAAT1N001").statut("disponible")
                .ouvrageId(3).build();
        livreMockList.add(livreEntity1);

        LivreEntity livreEntity2 = LivreEntity.builder().id(2).refBibliotheque("TIFRHARAAT1N002").statut("indisponible")
                .ouvrageId(3).build();
        livreMockList.add(livreEntity2);

        // Liste des emprunts
        List<EmpruntEntity> empruntMockList = new ArrayList<>();

        EmpruntEntity empruntEntity1 = EmpruntEntity.builder().id(1).dateDebut(dateFormat.parse("2019-12-20"))
                .dateFin(dateFormat.parse("2020-01-13")) .statut("En cours").prolongation(false).livreId(7)
                .compteId(3).build();
        empruntMockList.add(empruntEntity1);

        EmpruntEntity empruntEntity2 = EmpruntEntity.builder().id(1).dateDebut(dateFormat.parse("2019-01-20"))
                .dateFin(dateFormat.parse("2020-02-23")) .statut("Rendu").prolongation(false).livreId(2)
                .compteId(3).build();
        empruntMockList.add(empruntEntity2);

        when(livreRepository.findAllLivreByOuvrageId(anyInt())).thenReturn(livreMockList);
        when(empruntRepository.findAllEmpruntByOuvrageId(anyInt())).thenReturn(empruntMockList);

        final List<EmpruntEntity> empruntEntityList = empruntServiceImpl.getAllEmpruntByOuvrageId(3);

        Assertions.assertEquals(empruntEntityList.get(0).getLivreId(), livreMockList.get(0).getId());
        Assertions.assertEquals(empruntEntityList.get(1).getLivreId(), livreMockList.get(1).getId());

    }

    @Test
    void addEmprunt() throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        EmpruntEntity empruntMock = EmpruntEntity.builder().id(1).dateDebut(dateFormat.parse("2019-12-20"))
                .dateFin(dateFormat.parse("2020-01-13")).statut("En cours").prolongation(false).livreId(2)
                .compteId(3).build();

        when(empruntRepository.save(empruntMock)).thenThrow(DataIntegrityViolationException.class);
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            empruntServiceImpl.addEmprunt(empruntMock);
        });
    }

    @Test
    void updateEmprunt() throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        EmpruntEntity empruntMock = EmpruntEntity.builder().id(1).dateDebut(dateFormat.parse("2019-12-20"))
                .dateFin(dateFormat.parse("2020-01-13")).statut("En cours").prolongation(false).livreId(2)
                .compteId(3).build();

        when(empruntRepository.save(empruntMock)).thenThrow(DataIntegrityViolationException.class);
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            empruntServiceImpl.updateEmprunt(empruntMock);
        });
    }
}