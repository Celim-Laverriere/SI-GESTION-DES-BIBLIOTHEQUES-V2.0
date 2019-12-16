package org.bibliotheque.security.service;

import org.bibliotheque.repository.LoginRepository;
import org.bibliotheque.security.entity.Users;
import org.bibliotheque.wsdl.CompteType;
import org.bibliotheque.wsdl.ServiceStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpSession;
import java.util.*;

@Service
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private HttpSession session;

    @Autowired
    private Users users;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        Collection<SimpleGrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(users.getUserRole()));

        /**@see LoginRepository#loginCompte(String, String)*/
        Optional<ServiceStatus> serviceStatus = loginRepository.loginCompte
                (authentication.getName(), authentication.getCredentials().toString());


        if (serviceStatus.get().getStatusCode().equals("SUCCESS")){

            /**@see LoginRepository#getCompteAfterLoginSuccess(String)*/
            CompteType compteType = loginRepository.getCompteAfterLoginSuccess(authentication.getName());
            Users user = new Users(compteType.getId(), compteType.getPrenom());

            session.setAttribute("user", user);

            return new UsernamePasswordAuthenticationToken
                    (authentication.getName(), authentication.getCredentials().toString(), roles);
        } else {
            session.setAttribute("loginError", serviceStatus.get().getStatusCode());
            throw new BadCredentialsException("Authentication failed");
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
