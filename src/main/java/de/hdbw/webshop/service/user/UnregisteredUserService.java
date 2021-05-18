package de.hdbw.webshop.service.user;

import de.hdbw.webshop.exception.exceptions.UserNotFoundException;
import de.hdbw.webshop.model.users.entity.AllUsersEntity;
import de.hdbw.webshop.model.users.entity.UnregisteredUserEntity;
import de.hdbw.webshop.repository.user.UnregisteredUserRepository;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import java.time.LocalDate;

@Service
@CommonsLog
public class UnregisteredUserService {

    private final UnregisteredUserRepository unregisteredUserRepository;
    private AllUsersService allUsersService;

    public UnregisteredUserService(UnregisteredUserRepository unregisteredUserRepository) {
        this.unregisteredUserRepository = unregisteredUserRepository;
    }

    @Autowired
    public void setAllUsersService(AllUsersService allUsersService) {
        this.allUsersService = allUsersService;
    }

    public UnregisteredUserEntity createNewUnregisteredUser(String jsessionid) {
        AllUsersEntity allUsersEntity = allUsersService.createNewAllUsersEntity();
        LocalDate expiringDate = LocalDate.now().plusWeeks(2);
        UnregisteredUserEntity unregisteredUserEntity = new UnregisteredUserEntity(jsessionid, expiringDate, allUsersEntity);
        return unregisteredUserRepository.save(unregisteredUserEntity);
    }

    public UnregisteredUserEntity findByJsessionid (String jsessionid) {
        log.debug("Returning UnregisteredUser by Jsessionid: " + jsessionid);
        return unregisteredUserRepository.findByJsessionid(jsessionid).orElseThrow(
                () ->new UserNotFoundException());
    }

    public UnregisteredUserEntity findUnregisteredUserEntityBySessionId (String jsessionid) {
        try {
           return findByJsessionid(jsessionid);
        } catch (UserNotFoundException notFoundException) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Couldn't load anonymous user",
                    notFoundException
            );
        }
    }

    public boolean unregisteredUserForSessionExists(String jsessionid) {
        try {
            UnregisteredUserEntity unregisteredUserEntity = findByJsessionid(jsessionid);
            log.debug("Unregistered User with the jsessionid: " + jsessionid + " has the UnregisteredUserId: "
            + unregisteredUserEntity.getId());
            return true;
        } catch (UserNotFoundException e) {
            log.debug("There is no unregistered User for the Session: " + jsessionid);
            return false;
        }
    }

    public void deleteUnregisteredUserByJsessionid(String jsessionid) {
        unregisteredUserRepository.deleteByJsessionid(jsessionid);
    }
}
