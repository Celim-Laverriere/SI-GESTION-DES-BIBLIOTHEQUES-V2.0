package org.bibliotheque.service.contract;

import org.bibliotheque.entity.CompteEntity;

public interface LoginService {

    CompteEntity getCompteByMailAndPassword(String mail, String mot_de_passe);

    CompteEntity getCompteByMail(String mail);

}
