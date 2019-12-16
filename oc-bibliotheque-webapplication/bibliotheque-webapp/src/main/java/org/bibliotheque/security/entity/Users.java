package org.bibliotheque.security.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter @Setter
@NoArgsConstructor
public class Users {

    private Integer userId;
    private String userPrenom;
    private String userRole = "ROLE_USER";

    public Users(Integer userId, String userPrenom) {
        this.userId = userId;
        this.userPrenom = userPrenom;
    }

}
