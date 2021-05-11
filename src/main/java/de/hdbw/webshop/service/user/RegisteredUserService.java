package de.hdbw.webshop.service.user;

import de.hdbw.webshop.dto.UserRegistrationFormDTO;
import de.hdbw.webshop.exception.exceptions.UserNotFoundException;
import de.hdbw.webshop.model.users.AllUsersEntity;
import de.hdbw.webshop.model.users.RegisteredUserEntity;
import de.hdbw.webshop.repository.user.RegisteredUserRepository;
import org.springframework.stereotype.Service;

@Service
public class RegisteredUserService {

    private final RegisteredUserRepository registeredUserRepository;

    public RegisteredUserService(RegisteredUserRepository registeredUserRepository) {
        this.registeredUserRepository = registeredUserRepository;
    }

    public RegisteredUserEntity createNewRegisteredUser(UserRegistrationFormDTO userRegistrationFormDTO,
                                                        AllUsersEntity allUsersEntity) {
        return new RegisteredUserEntity(userRegistrationFormDTO.getEmail(), userRegistrationFormDTO.getFirstName(),
                userRegistrationFormDTO.getSecondName(), true, allUsersEntity);
    }

    public RegisteredUserEntity findRegisteredUserEntity (String email) {
        return registeredUserRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException()
        );
    }

}
