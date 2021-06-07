package de.hdbw.webshop.listener;

import de.hdbw.webshop.model.users.entity.UnregisteredUserEntity;
import de.hdbw.webshop.service.user.UnregisteredUserService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Enumeration;

@Component
@CommonsLog
public class MySessionListener implements HttpSessionListener {

    private final UnregisteredUserService unregisteredUserService;

    @Autowired
    public MySessionListener(UnregisteredUserService unregisteredUserService) {
        this.unregisteredUserService = unregisteredUserService;
    }

    @Override
    public void sessionCreated (HttpSessionEvent httpsessionEvent) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        HttpSession session = httpsessionEvent.getSession();
        String jsessionid = session.getId();
        if (authentication==null || authentication.getPrincipal().equals("anonymousUser")) {
            log.info("Creating new Session for Unregistered User with jsessionid: " + jsessionid);
            int expireDateInSeconds = 50*60*24*14;
            session.setMaxInactiveInterval(expireDateInSeconds);
            UnregisteredUserEntity newUnregisteredUser = unregisteredUserService.createNewUnregisteredUser(jsessionid);
            log.info("Creating new UnregisteredUser with the allUserId: " + newUnregisteredUser.getAllUsers().getId());
        } else {
            log.info("Creating new Session for Registered User with jsessionid: " + jsessionid);
        }
    }

//    @Override
//    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
//        Enumeration<String> attributeNames = httpSessionEvent.getSession().getAttributeNames();
//        System.out.println(httpSessionEvent.getSession().getId());
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication.getPrincipal().equals("anonymousUser")) {
//            HttpSession session = httpSessionEvent.getSession();
//            unregisteredUserService.deleteUnregisteredUserByJsessionid(session.getId());
//            log.debug("Deleting Unregistered User with the jsessionid: " + session.getId() + " because his session expired");
//        }
//    }
}
