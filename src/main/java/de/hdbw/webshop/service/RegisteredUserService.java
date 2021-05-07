package de.hdbw.webshop.service;

import de.hdbw.webshop.dto.UserRegistrationFormDTO;
import de.hdbw.webshop.model.user.AllUsersEntity;
import de.hdbw.webshop.model.user.RegisteredUserEntity;
import de.hdbw.webshop.model.user.UserPasswordEntity;
import org.springframework.stereotype.Service;

@Service
public class RegisteredUserService {

    public RegisteredUserEntity createNewRegisteredUser(UserRegistrationFormDTO userRegistrationFormDTO,
                                                        AllUsersEntity allUsersEntity) {
        return new RegisteredUserEntity(userRegistrationFormDTO.getEmail(), userRegistrationFormDTO.getFirstName(),
                userRegistrationFormDTO.getSecondName(), true, allUsersEntity);
    }
}
