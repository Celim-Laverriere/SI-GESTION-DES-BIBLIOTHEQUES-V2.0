package org.bibliotheque.repository;

import org.bibliotheque.client.LoginClient;
import org.bibliotheque.wsdl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class LoginRepository {

    @Autowired
    private LoginClient client;

    /**
     * ==== GET COMPTE BY MAIL AND PASSWORD FOR LOGIN ====
     * @param mail
     * @param password
     * @return Optional ServiceStatus
     * @see LoginClient#login(String, String)
     */
    public Optional<ServiceStatus> loginCompte(String mail, String password){
        LoginResponse response = client.login(mail, password);
        return Optional.ofNullable(response.getServiceStatus());
    }

    /**
     * GET COMPTE AFTER LOGIN SUCCESS
     * @param mail
     * @return CompteType
     * @see LoginClient#getCompteAfterLoginSuccess(String)
     */
    public CompteType getCompteAfterLoginSuccess(String mail){
        GetCompteAfterLoginSuccessResponse response = client.getCompteAfterLoginSuccess(mail);
        return response.getCompteType();
    }

}
