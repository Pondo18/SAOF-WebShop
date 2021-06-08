package de.hdbw.webshop.service.authentication;

import de.hdbw.webshop.dto.registration.UserRegistrationFormDTO;
import de.hdbw.webshop.model.users.entity.AllUsersEntity;
import de.hdbw.webshop.model.users.entity.RegisteredUsersEntity;
import de.hdbw.webshop.model.users.entity.UserPasswordEntity;
import de.hdbw.webshop.service.session.SessionService;
import de.hdbw.webshop.service.user.AllUsersService;
import de.hdbw.webshop.service.user.RegisteredUserService;
import de.hdbw.webshop.service.user.UserPasswordService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class UserRegistrationService {

    private final RegisteredUserService registeredUserService;
    private final AllUsersService allUsersService;
    private final UserPasswordService userPasswordService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SessionService sessionService;
    private final AuthenticationService authenticationService;

    public UserRegistrationService(RegisteredUserService registeredUserService, AllUsersService allUsersService, UserPasswordService userPasswordService, BCryptPasswordEncoder bCryptPasswordEncoder, SessionService sessionService, AuthenticationService authenticationService) {
        this.registeredUserService = registeredUserService;
        this.allUsersService = allUsersService;
        this.userPasswordService = userPasswordService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.sessionService = sessionService;
        this.authenticationService = authenticationService;
    }

    public AllUsersEntity doRegistration(UserRegistrationFormDTO userRegistrationFormDTO,
                                         HttpServletRequest request,
                                         HttpServletResponse response) {
        UserPasswordEntity userPasswordEntity = registerNewUser(userRegistrationFormDTO);
        String email = userRegistrationFormDTO.getEmail();
        String password = userRegistrationFormDTO.getPassword();
        authenticationService.doAutoLoginAfterUserRegistration(email, password, request, response);
        return userPasswordEntity.getRegisteredUser().getAllUsers();
    }

    public UserPasswordEntity registerNewUser (UserRegistrationFormDTO userRegistrationFormDTO) {
        AllUsersEntity allUsersEntity = allUsersService.createNewAllUsersEntity();
        RegisteredUsersEntity registeredUserEntity = registeredUserService.createNewRegisteredUser(
                userRegistrationFormDTO, allUsersEntity
        );
        UserPasswordEntity userPasswordEntity = createNewUserPassword(registeredUserEntity,
                userRegistrationFormDTO);
        return userPasswordService.saveToDatabase(userPasswordEntity);
    }

    public UserPasswordEntity createNewUserPassword (RegisteredUsersEntity registeredUserEntity,
                                                     UserRegistrationFormDTO userRegistrationFormDTO) {
        String userPassword = userRegistrationFormDTO.getPassword();
        return new UserPasswordEntity(registeredUserEntity, bCryptPasswordEncoder.encode(userPassword));
    }
}
