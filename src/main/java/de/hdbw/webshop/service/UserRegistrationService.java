package de.hdbw.webshop.service;

import de.hdbw.webshop.dto.UserRegistrationFormDTO;
import de.hdbw.webshop.model.user.AllUsersEntity;
import de.hdbw.webshop.model.user.RegisteredUserEntity;
import de.hdbw.webshop.model.user.UserPasswordEntity;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationService {

    private final RegisteredUserService registeredUserService;
    private final AllUsersService allUsersService;
    private final UserPasswordService userPasswordService;

    public UserRegistrationService(RegisteredUserService registeredUserService, AllUsersService allUsersService, UserPasswordService userPasswordService) {
        this.registeredUserService = registeredUserService;
        this.allUsersService = allUsersService;
        this.userPasswordService = userPasswordService;
    }

    public UserPasswordEntity registerNewUser (UserRegistrationFormDTO userRegistrationFormDTO) {
        AllUsersEntity allUsersEntity = allUsersService.createNewAllUsersEntity();
        RegisteredUserEntity registeredUserEntity = registeredUserService.createNewRegisteredUser(
                userRegistrationFormDTO, allUsersEntity
        );
        UserPasswordEntity userPasswordEntity = userPasswordService.createNewUserPassword(registeredUserEntity,
                userRegistrationFormDTO);
        return userPasswordService.saveToDatabase(userPasswordEntity);
    }

}
