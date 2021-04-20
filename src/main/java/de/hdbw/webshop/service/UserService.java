package de.hdbw.webshop.service;

import de.hdbw.webshop.dto.UserRegistrationFormDTO;
import de.hdbw.webshop.exception.RoleNotFoundException;
import de.hdbw.webshop.model.auth.RolesEntity;
import de.hdbw.webshop.model.auth.UserEntity;
import de.hdbw.webshop.model.auth.UserRoleEntity;
import de.hdbw.webshop.repository.RolesRepository;
import de.hdbw.webshop.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRoleRepository userRoleRepository;
    private final RolesRepository rolesRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRoleRepository userRoleRepository, RolesRepository rolesRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRoleRepository = userRoleRepository;
        this.rolesRepository = rolesRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public UserRoleEntity registerNewUser (UserRegistrationFormDTO userRegistrationForm) {
        System.out.println(userRegistrationForm.toString());
        UserEntity userEntity = getUserEntity(userRegistrationForm);
        RolesEntity rolesEntity = getRolesEntity(2);
        return userRoleRepository.save(new UserRoleEntity(userEntity, rolesEntity));
    }



    public UserEntity getUserEntity(UserRegistrationFormDTO userRegistrationForm) {
        return new UserEntity(
                userRegistrationForm.getFirstName(),
                userRegistrationForm.getSecondName(),
                userRegistrationForm.getEmail(),
                bCryptPasswordEncoder.encode(userRegistrationForm.getPassword()),
                true);
    }

    public RolesEntity getRolesEntity(int roleId) {
        return rolesRepository.findById((long) roleId).orElseThrow(() -> new RoleNotFoundException(
                "RoleEntityDTO by Id " +
                        roleId +
                        " was not found"));
    }
}
