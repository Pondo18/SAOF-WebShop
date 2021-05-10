package de.hdbw.webshop.service.user;

import de.hdbw.webshop.model.users.AllUsersEntity;
import de.hdbw.webshop.model.users.UnregisteredUserEntity;
import de.hdbw.webshop.repository.UnregisteredUserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UnregisteredUserService {

    private final UnregisteredUserRepository unregisteredUserRepository;
    private final AllUsersService allUsersService;

    public UnregisteredUserService(UnregisteredUserRepository unregisteredUserRepository, AllUsersService allUsersService) {
        this.unregisteredUserRepository = unregisteredUserRepository;
        this.allUsersService = allUsersService;
    }

    public UnregisteredUserEntity createNewUnregisteredUser(String uuid, LocalDate expireDate) {
        AllUsersEntity allUsersEntity = allUsersService.createNewAllUsersEntity();
        UnregisteredUserEntity unregisteredUserEntity = new UnregisteredUserEntity(uuid, expireDate, allUsersEntity);
        return unregisteredUserRepository.save(unregisteredUserEntity);
    }
}
