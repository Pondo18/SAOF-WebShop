package de.hdbw.webshop.service.session;

import de.hdbw.webshop.model.users.User;
import de.hdbw.webshop.service.user.RegisteredUserService;
import de.hdbw.webshop.service.user.UnregisteredUserService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@CommonsLog
@Service
public class SessionService {

    private final UnregisteredUserService unregisteredUserService;
    private final RegisteredUserService registeredUserService;


    public SessionService(UnregisteredUserService unregisteredUserService, RegisteredUserService registeredUserService) {
        this.unregisteredUserService = unregisteredUserService;
        this.registeredUserService = registeredUserService;
    }


    public boolean currentSessionIsAnonymous (String jsessionid) {
        return unregisteredUserService.unregisteredUserForSessionExists(jsessionid);
    }

    public User getCurrentUserBySession(HttpSession session, Authentication authentication) {
        String jsessionid = session.getId();
        if (currentSessionIsAnonymous(jsessionid)) {
            return unregisteredUserService
                    .findUnregisteredUserEntityBySessionId(jsessionid);
        } else {
            String email = authentication.getName();
            return registeredUserService.findRegisteredUserEntity(email);
        }
    }
}

