package de.hdbw.webshop.service;

import de.hdbw.webshop.dto.UserRegistrationFormDTO;
import de.hdbw.webshop.model.users.AllUsersEntity;
import de.hdbw.webshop.model.users.RegisteredUserEntity;
import de.hdbw.webshop.model.users.UserPasswordEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserRegistrationService {

    private final RegisteredUserService registeredUserService;
    private final AllUsersService allUsersService;
    private final UserPasswordService userPasswordService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SessionService sessionService;

    public UserRegistrationService(RegisteredUserService registeredUserService, AllUsersService allUsersService, UserPasswordService userPasswordService, BCryptPasswordEncoder bCryptPasswordEncoder, SessionService sessionService) {
        this.registeredUserService = registeredUserService;
        this.allUsersService = allUsersService;
        this.userPasswordService = userPasswordService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.sessionService = sessionService;
    }

    public void doRegistration(UserRegistrationFormDTO userRegistrationFormDTO, HttpServletRequest request) {
        registerNewUser(userRegistrationFormDTO);
        String email = userRegistrationFormDTO.getEmail();
        String password = userRegistrationFormDTO.getPassword();
        sessionService.doAutoLogin(email, password, request);
    }

    public UserPasswordEntity registerNewUser (UserRegistrationFormDTO userRegistrationFormDTO) {
        AllUsersEntity allUsersEntity = allUsersService.createNewAllUsersEntity();
        RegisteredUserEntity registeredUserEntity = registeredUserService.createNewRegisteredUser(
                userRegistrationFormDTO, allUsersEntity
        );
        UserPasswordEntity userPasswordEntity = createNewUserPassword(registeredUserEntity,
                userRegistrationFormDTO);
        return userPasswordService.saveToDatabase(userPasswordEntity);
    }

    public UserPasswordEntity createNewUserPassword (RegisteredUserEntity registeredUserEntity,
                                                     UserRegistrationFormDTO userRegistrationFormDTO) {
        String userPassword = userRegistrationFormDTO.getPassword();
        return new UserPasswordEntity(registeredUserEntity, bCryptPasswordEncoder.encode(userPassword));
    }
}
