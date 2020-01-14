package org.bibliotheque.service.impl;

import org.bibliotheque.entity.CompteEntity;
import org.bibliotheque.repository.LoginRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@DisplayName( "Testing LoginServiceImpl" )
class LoginServiceImplTest {

    @Mock
    LoginRepository loginRepository;

    @InjectMocks
    LoginServiceImpl loginService;

    @BeforeEach
    void setUp( ) {
        MockitoAnnotations.initMocks( this );
    }

    @Test
    final void getCompteByMail( ) {

        CompteEntity compteMock = CompteEntity.builder()
                .id( 0 )
                .mail( "" )
                .prenom( "" )
                .nom( "" )
                .adresse( "" )
                .codePostal( 0 )
                .ville( "" )
                .motDePasse( "" )
                .numCarteBibliotheque( 0 )
                .numDomicile( 0 )
                .numPortable( 0 )
                .build();

        when(loginRepository.findByMail( anyString() )).thenReturn( compteMock );

        final CompteEntity compteEntity = loginService.getCompteByMail( "email à tester" );

        Assertions.assertNotNull( compteEntity );
//        Assertions.assertEquals( "nom à obtenir", compteEntity.getNom() );
    }
}

