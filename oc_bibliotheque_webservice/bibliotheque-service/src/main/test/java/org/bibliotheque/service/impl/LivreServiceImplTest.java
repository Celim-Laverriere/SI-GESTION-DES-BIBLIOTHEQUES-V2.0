package org.bibliotheque.service.impl;

import org.bibliotheque.entity.LivreEntity;
import org.bibliotheque.repository.LivreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class LivreServiceImplTest {

    @Mock
    LivreRepository livreRepository;

    @InjectMocks
    LivreServiceImpl livreServiceImpl;

    @BeforeEach
    void setUp() throws  Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getLivreById() {

        LivreEntity livreMock = LivreEntity.builder()
                .refBibliotheque("TIFRHARAAT1N001").statut("disponible").ouvrageId(3).build();

        when(livreRepository.findById(3)).thenReturn(java.util.Optional.ofNullable(livreMock));
        final LivreEntity livreEntity = livreServiceImpl.getLivreById(3);
        Assertions.assertEquals(livreEntity.getRefBibliotheque(), livreMock.getRefBibliotheque());
    }

    @Test
    void getAllLivres() {

        List<LivreEntity> livreMockList = new ArrayList<>();

        LivreEntity livreMock1 = LivreEntity.builder().id(7).refBibliotheque("TIFRHARAAT1N001").statut("disponible")
                .ouvrageId(3).build();
        livreMockList.add(livreMock1);

        LivreEntity livreMock2 = LivreEntity.builder().id(2).refBibliotheque("TISFFPHDT1N002").statut("indisponible")
                .ouvrageId(1).build();
        livreMockList.add(livreMock2);

        when(livreRepository.findAll()).thenReturn(livreMockList);
        final List<LivreEntity> livreEntityList = livreServiceImpl.getAllLivres();
        Assertions.assertEquals(livreEntityList.size(), 2);


    }
}