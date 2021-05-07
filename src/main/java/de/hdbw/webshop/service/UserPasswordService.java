package de.hdbw.webshop.service;

import de.hdbw.webshop.model.users.RegisteredUserEntity;
import de.hdbw.webshop.model.users.UserPasswordEntity;
import de.hdbw.webshop.repository.UserPasswordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserPasswordService {

    private final UserPasswordRepository userPasswordRepository;

    @Autowired
    public UserPasswordService(UserPasswordRepository userPasswordRepository) {
        this.userPasswordRepository = userPasswordRepository;
    }

    public UserPasswordEntity saveToDatabase (UserPasswordEntity userPasswordEntity) {
        return userPasswordRepository.save(userPasswordEntity);
    }

    public UserPasswordEntity findUserPasswordEntity (RegisteredUserEntity registeredUserEntity) {
        return userPasswordRepository.findByRegisteredUser(registeredUserEntity);
    }
}
