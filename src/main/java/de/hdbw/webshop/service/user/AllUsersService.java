package de.hdbw.webshop.service.user;

import de.hdbw.webshop.exception.exceptions.UserNotFoundException;
import de.hdbw.webshop.model.users.entity.AllUsersEntity;
import de.hdbw.webshop.model.users.User;
import de.hdbw.webshop.model.users.entity.UnregisteredUserEntity;
import de.hdbw.webshop.repository.user.AllUsersRepository;
import de.hdbw.webshop.service.session.SessionService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
@CommonsLog
public class AllUsersService {

    private final SessionService sessionService;
    private final AllUsersRepository allUsersRepository;
    private final RegisteredUserService registeredUserService;
    private final UnregisteredUserService unregisteredUserService;

    public AllUsersService(SessionService sessionService, AllUsersRepository allUsersRepository, RegisteredUserService registeredUserService, UnregisteredUserService unregisteredUserService) {
        this.sessionService = sessionService;
        this.allUsersRepository = allUsersRepository;
        this.registeredUserService = registeredUserService;
        this.unregisteredUserService = unregisteredUserService;
    }

    public AllUsersEntity createNewAllUsersEntity() {
        return new AllUsersEntity();
    }

    public AllUsersEntity getCurrentUserBySession(HttpSession session, Authentication authentication) {
        User currentUser = sessionService.getCurrentUserBySession(session, authentication);
        log.debug("Finding User for Session " + session.getId());
        return getCurrentUser(currentUser);
    }

    public AllUsersEntity getCurrentUser(User currentUser) {
        return allUsersRepository.findById(currentUser.getUserId()).orElseThrow(
                () -> new UserNotFoundException());
    }

    public AllUsersEntity getCurrentRegisteredUser(Authentication authentication) {
        try {
            String email = authentication.getName();
            User registeredUser = registeredUserService.findRegisteredUserEntity(email);
            return getCurrentUser(registeredUser);
        } catch (Exception e) {
            throw new UserNotFoundException();
        }
    }

    public AllUsersEntity getCurrentUnregisteredUser (String jsessionid) {
        UnregisteredUserEntity unregisteredUserEntity = unregisteredUserService
                .findUnregisteredUserEntityBySessionId(jsessionid);
        return unregisteredUserEntity.getAllUsers();
    }
}
