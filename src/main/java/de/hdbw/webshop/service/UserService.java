package de.hdbw.webshop.service;

import de.hdbw.webshop.dto.UserRegistrationFormDTO;
import de.hdbw.webshop.exception.exceptions.RoleNotFoundException;
import de.hdbw.webshop.exception.exceptions.UserAlreadyExistsException;
import de.hdbw.webshop.model.auth.RolesEntity;
import de.hdbw.webshop.model.auth.UserEntity;
import de.hdbw.webshop.model.auth.UserRoleEntity;
import de.hdbw.webshop.repository.RolesRepository;
import de.hdbw.webshop.repository.UserRepository;
import de.hdbw.webshop.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserService {

    private final UserRoleRepository userRoleRepository;
    private final RolesRepository rolesRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRoleRepository userRoleRepository, RolesRepository rolesRepository, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRoleRepository = userRoleRepository;
        this.rolesRepository = rolesRepository;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public UserRoleEntity registerNewUser (UserRegistrationFormDTO userRegistrationForm) {
        if (!emailExists(userRegistrationForm.getEmail())) {
            UserEntity userEntity = getUserEntity(userRegistrationForm);
            try {
                RolesEntity rolesEntity = getRolesEntity(2);
                return userRoleRepository.save(new UserRoleEntity(userEntity, rolesEntity));
            } catch (RoleNotFoundException roleNotFoundException) {
                throw new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Role Not Found",
                        roleNotFoundException
                );
            }
        } else {
            throw new UserAlreadyExistsException();
        }
    }

    public UserEntity getUserEntity(UserRegistrationFormDTO userRegistrationForm) {
        return new UserEntity(
                userRegistrationForm.getFirstName(),
                userRegistrationForm.getSecondName(),
                userRegistrationForm.getEmail(),
                bCryptPasswordEncoder.encode(userRegistrationForm.getPassword()),
                true);
    }

    public RolesEntity getRolesEntity(long roleId) {
        return rolesRepository.findById(roleId).orElseThrow(() -> new RoleNotFoundException());
    }

    private boolean emailExists(final String email) {
        Optional<UserEntity> userEntity = userRepository.findUserEntityByEmail(email);
        return userEntity.isPresent();
    }
}
