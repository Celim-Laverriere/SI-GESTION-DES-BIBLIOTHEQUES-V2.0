package org.bibliotheque.service.impl;

import org.bibliotheque.entity.CompteEntity;
import org.bibliotheque.repository.CompteRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CompteServiceImplTest {

    @Mock
    CompteRepository compteRepository;

    @InjectMocks
    CompteServiceImpl compteServiceImpl;

    @BeforeEach
    void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getCompteById() {

        CompteEntity compteMock = CompteEntity.builder().id( 1 ).mail( "evancarre@fakeemail.tld" ).prenom( "Evan" )
                .nom( "Carre" ).adresse( "65 Rue Basfroi" ).codePostal( 79170 ).ville( "Tilly" ).motDePasse( "Evan" )
                .numCarteBibliotheque( 656310049 ).numDomicile( null ).numPortable( 646864294 ).build();

        when(compteRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(compteMock));
        final CompteEntity compteEntity = compteServiceImpl.getCompteById(1);
        Assertions.assertEquals(compteMock.getAdresse(), compteEntity.getAdresse());

        when(compteRepository.save(compteMock)).thenThrow(NoSuchElementException.class);
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            compteServiceImpl.getCompteById(101);
        });

    }

    @Test
    void getAllComptes() {

        List<CompteEntity> compteMockList = new ArrayList<>();

        CompteEntity compteMock1 = CompteEntity.builder().id( 1 ).mail( "evancarre@fakeemail.tld" ).prenom( "Evan" )
                .nom( "Carre" ).adresse( "65 Rue Basfroi" ).codePostal( 79170 ).ville( "Tilly" ).motDePasse( "Evan" )
                .numCarteBibliotheque( 656310049 ).numDomicile( null ).numPortable( 646864294 ).build();
        compteMockList.add(compteMock1);

        CompteEntity compteMock2 = CompteEntity.builder().id( 1 ).mail( "julie_dakota@dakota.tld" ).prenom( "Julie" )
                .nom( "Dakota" ).adresse( "2 Rue Bastion" ).codePostal( 79170 ).ville( "Tilly" ).motDePasse( "dakota" )
                .numCarteBibliotheque( 656310449 ).numDomicile( null ).numPortable( 646868956 ).build();
        compteMockList.add(compteMock2);

        when(compteRepository.findAll()).thenReturn(compteMockList);
        final List<CompteEntity> compteEntityList = compteServiceImpl.getAllComptes();
        Assertions.assertEquals(compteEntityList.size(), 2);
    }

    @Test
    void addCompte() {

        CompteEntity compteMock = CompteEntity.builder().id( 1 ).mail( "evancarre@fakeemail.tld" ).prenom( "Evan" )
                .nom( "Carre" ).adresse( "65 Rue Basfroi" ).codePostal( 79170 ).ville( "Tilly" ).motDePasse( "Evan" )
                .numCarteBibliotheque( 656310049 ).numDomicile( null ).numPortable( 646864294 ).build();

        when(compteRepository.save(compteMock)).thenThrow(DataIntegrityViolationException.class);
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            compteServiceImpl.addCompte(compteMock);
        });
    }

    @Test
    void updateCompte() {

        CompteEntity compteMock = CompteEntity.builder().id( 101 ).mail( "evancarre@fakeemail.tld" ).prenom( "Evan" )
                .nom( "Carre" ).adresse( "65 Rue Basfroi" ).codePostal( 79170 ).ville( "Tilly" ).motDePasse( "Evan" )
                .numCarteBibliotheque( 656310049 ).numDomicile( null ).numPortable( 646864294 ).build();

        when(compteRepository.save(compteMock)).thenThrow(DataIntegrityViolationException.class);
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            compteServiceImpl.updateCompte(compteMock);
        });

    }


}