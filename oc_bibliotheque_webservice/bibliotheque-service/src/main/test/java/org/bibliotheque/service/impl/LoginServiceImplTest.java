package org.bibliotheque.service.impl;

import org.bibliotheque.entity.CompteEntity;
import org.bibliotheque.repository.CompteRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;

@DisplayName( "Testing LoginServiceImpl" )
class LoginServiceImplTest {

    @Mock
    CompteRepository compteRepository;

    @InjectMocks
    LoginServiceImpl loginService;

    @BeforeEach
    void setUp( ) {
        MockitoAnnotations.initMocks( this );
    }

    @Test
    void getCompteByMailAndPassword(){

        CompteEntity compteMock = CompteEntity.builder().id( 1 ).mail( "evancarre@fakeemail.tld" ).prenom( "Evan" )
                .nom( "Carre" ).adresse( "65 Rue Basfroi" ).codePostal( 79170 ).ville( "Tilly" ).motDePasse( "Evan" )
                .numCarteBibliotheque( 656310049 ).numDomicile( null ).numPortable( 646864294 ).build();

        when(compteRepository.findByMailAndMotDePasse("evancarre@fakeemail.tld", "Evan" ))
                .thenReturn( compteMock );

        final CompteEntity compteEntity1 = loginService.getCompteByMailAndPassword( "evancarre@fakeemail.tld", "Evan" );

        Assertions.assertEquals(compteEntity1.getId(), compteMock.getId());
        Assertions.assertEquals(compteEntity1.getAdresse(), compteMock.getAdresse());
        Assertions.assertEquals(compteEntity1.getNumCarteBibliotheque(), compteMock.getNumCarteBibliotheque());

        when(compteRepository.findByMailAndMotDePasse("evancarre@fakeemail.tld", "Evan" ))
                .thenThrow(NullPointerException.class);

        Assertions.assertThrows(NullPointerException.class, () -> {
            loginService.getCompteByMailAndPassword( "evancarre@fakeemail.tld", "Evan");
        });
    }

    @Test
    void getCompteByMail(){

        CompteEntity compteMock = CompteEntity.builder().id( 1 ).mail( "evancarre@fakeemail.tld" ).prenom( "Evan" )
                .nom( "Carre" ).adresse( "65 Rue Basfroi" ).codePostal( 79170 ).ville( "Tilly" ).motDePasse( "Evan" )
                .numCarteBibliotheque( 656310049 ).numDomicile( null ).numPortable( 646864294 ).build();

        when(compteRepository.findByMail("evancarre@fakeemail.tld"))
                .thenReturn( compteMock );

        final CompteEntity compteEntity = loginService.getCompteByMail("evancarre@fakeemail.tld");

        Assertions.assertEquals(compteEntity.getId(), compteMock.getId());
        Assertions.assertEquals(compteEntity.getAdresse(), compteMock.getAdresse());
        Assertions.assertEquals(compteEntity.getNumCarteBibliotheque(), compteMock.getNumCarteBibliotheque());
    }
}

