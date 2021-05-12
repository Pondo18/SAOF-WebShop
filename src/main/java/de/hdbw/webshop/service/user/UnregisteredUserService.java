package de.hdbw.webshop.service.user;

import de.hdbw.webshop.model.users.AllUsersEntity;
import de.hdbw.webshop.model.users.UnregisteredUserEntity;
import de.hdbw.webshop.repository.UnregisteredUserRepository;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSessionEvent;

@Service
@CommonsLog
public class UnregisteredUserService {

    private final UnregisteredUserRepository unregisteredUserRepository;
    private final AllUsersService allUsersService;

    public UnregisteredUserService(UnregisteredUserRepository unregisteredUserRepository, AllUsersService allUsersService) {
        this.unregisteredUserRepository = unregisteredUserRepository;
        this.allUsersService = allUsersService;
    }

    public UnregisteredUserEntity createNewUnregisteredUser(HttpSessionEvent httpSessionEvent) {
        AllUsersEntity allUsersEntity = allUsersService.createNewAllUsersEntity();
        String jsessionid = httpSessionEvent.getSession().getId();
        UnregisteredUserEntity unregisteredUserEntity = new UnregisteredUserEntity(jsessionid, allUsersEntity);
        log.debug("Creating new Unregistered User for session: " + jsessionid);
        return unregisteredUserRepository.save(unregisteredUserEntity);
    }
}
