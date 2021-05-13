package de.hdbw.webshop.service.user;

import de.hdbw.webshop.exception.exceptions.UserNotFoundException;
import de.hdbw.webshop.model.users.entity.AllUsersEntity;
import de.hdbw.webshop.model.users.User;
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

    public AllUsersService(SessionService sessionService, AllUsersRepository allUsersRepository) {
        this.sessionService = sessionService;
        this.allUsersRepository = allUsersRepository;
    }

    public AllUsersEntity createNewAllUsersEntity () {
        return new AllUsersEntity();
    }

    public AllUsersEntity getCurrentUserBySession(HttpSession session, Authentication authentication) {
       User currentUser = sessionService.getCurrentUserBySession(session, authentication);
       log.debug("Finding User for Session " + session.getId());
        return allUsersRepository.findById(currentUser.getUserId()).orElseThrow(
                () -> new UserNotFoundException());
    }
}
