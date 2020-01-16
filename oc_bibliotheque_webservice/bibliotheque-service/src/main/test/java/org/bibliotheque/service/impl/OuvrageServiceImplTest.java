package org.bibliotheque.service.impl;

import org.bibliotheque.entity.OuvrageEntity;
import org.bibliotheque.repository.OuvrageRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@DisplayName( "Testing OuvrageServiceImpl" )
class OuvrageServiceImplTest {

    @Mock
    OuvrageRepository ouvrageRepository;

    @InjectMocks
    OuvrageServiceImpl ouvrageServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks( this );
    }

    @Test
    void getOuvrageById() {

        OuvrageEntity ouvrageMock = OuvrageEntity.builder().id(2).titre("Fondation - Le cycle de Fondation Tome 1")
                .genre("science-fiction").resumer("En ce début de treizième millénaire...").auteur("Isaac Asimov")
                .editeur("Gallimard").ref("978-2070360536").build();

        when(ouvrageRepository.findById(2)).thenReturn(java.util.Optional.ofNullable(ouvrageMock));

        final OuvrageEntity ouvrageEntity = ouvrageServiceImpl.getOuvrageById(2);

        Assertions.assertEquals(ouvrageMock.getAuteur(), ouvrageEntity.getAuteur());
        Assertions.assertEquals(ouvrageEntity.getEditeur(), "Gallimard");
    }

    @Test
    void getAllOuvrages() {

        List<OuvrageEntity> ouvrageMockList = new ArrayList<>();
        OuvrageEntity ouvrageEntity1 = OuvrageEntity.builder().id(2).titre("Fondation - Le cycle de Fondation Tome 1")
                .genre("science-fiction").resumer("En ce début de treizième millénaire...").auteur("Isaac Asimov")
                .editeur("Gallimard").ref("978-2070360536").build();
        ouvrageMockList.add(ouvrageEntity1);

        OuvrageEntity ouvrageEntity2 = OuvrageEntity.builder().id(2).titre("Dune - Tome 1 : Dune")
                .genre("science-fiction").resumer("Il n'y a pas, dans tout l'Empire, de planète plus inhospitalière que Dune...")
                .auteur("Frank Patrick Herbert").editeur("Pocket").ref("978-2266233200").build();
        ouvrageMockList.add(ouvrageEntity2);

        OuvrageEntity ouvrageEntity3 = OuvrageEntity.builder().id(2).titre("Chronique du tueur de roi - Première journée Tome 1 : Le Nom du vent")
                .genre("Fantasy").resumer("Un homme prêt à mourir raconte sa propre vie, celle du plus grand magicien de tous les temps...")
                .auteur("Patrick Rothfuss").editeur("Bragelonne").ref("978-2352943556").build();
        ouvrageMockList.add(ouvrageEntity3);

        when(ouvrageRepository.findAll()).thenReturn(ouvrageMockList);

        final List<OuvrageEntity> ouvrageEntityList = ouvrageServiceImpl.getAllOuvrages();

        Assertions.assertEquals(ouvrageEntity1.getAuteur(), ouvrageEntityList.get(0).getAuteur());
        Assertions.assertEquals(ouvrageEntity2.getAuteur(), ouvrageEntityList.get(1).getAuteur());
        Assertions.assertEquals(ouvrageEntity3.getAuteur(), ouvrageEntityList.get(2).getAuteur());
    }

    @Test
    void getAllOuvagesByKeyword() {

        List<OuvrageEntity> ouvrageMockList = new ArrayList<>();

        OuvrageEntity ouvrageEntity1 = OuvrageEntity.builder().id(1).titre("Fondation - Le cycle de Fondation Tome 1")
                .genre("science-fiction").resumer("En ce début de treizième millénaire...").auteur("Isaac Asimov")
                .editeur("Gallimard").ref("978-2070360536").build();
        ouvrageMockList.add(ouvrageEntity1);

        when(ouvrageRepository.findAllOuvragesByKeyword("Isaac Asimov")).thenReturn((ouvrageMockList));
        final List<OuvrageEntity> ouvrageEntityList2 = ouvrageServiceImpl.getAllOuvagesByKeyword("Isaac Asimov");
        Assertions.assertEquals(ouvrageEntity1.getEditeur(), ouvrageEntityList2.get(0).getEditeur());

        ouvrageMockList.clear();

        OuvrageEntity ouvrageEntity2 = OuvrageEntity.builder().id(2).titre("Dune - Tome 1 : Dune")
                .genre("science-fiction").resumer("Il n'y a pas, dans tout l'Empire, de planète plus inhospitalière que Dune...")
                .auteur("Frank Patrick Herbert").editeur("Pocket").ref("978-2266233200").build();
        ouvrageMockList.add(ouvrageEntity2);

        when(ouvrageRepository.findAllOuvragesByKeyword("Dune")).thenReturn((ouvrageMockList));
        final List<OuvrageEntity> ouvrageEntityList1 = ouvrageServiceImpl.getAllOuvagesByKeyword("Dune");
        Assertions.assertEquals(ouvrageEntity2.getAuteur(), ouvrageEntityList1.get(0).getAuteur());

    }
}