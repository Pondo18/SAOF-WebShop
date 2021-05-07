package de.hdbw.webshop.service;

import de.hdbw.webshop.dto.UserRegistrationFormDTO;
import de.hdbw.webshop.model.user.RegisteredUserEntity;
import de.hdbw.webshop.model.user.UserPasswordEntity;
import de.hdbw.webshop.repository.UserPasswordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserPasswordService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserPasswordRepository userPasswordRepository;


    @Autowired
    public UserPasswordService(BCryptPasswordEncoder bCryptPasswordEncoder, UserPasswordRepository userPasswordRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userPasswordRepository = userPasswordRepository;
    }

    public UserPasswordEntity createNewUserPassword (RegisteredUserEntity registeredUserEntity,
                                                     UserRegistrationFormDTO userRegistrationFormDTO) {
        String userPassword = userRegistrationFormDTO.getPassword();
        return new UserPasswordEntity(registeredUserEntity, bCryptPasswordEncoder.encode(userPassword));
    }

    public UserPasswordEntity saveToDatabase (UserPasswordEntity userPasswordEntity) {
        return userPasswordRepository.save(userPasswordEntity);
    }
}
