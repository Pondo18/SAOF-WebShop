package de.hdbw.webshop.listener;

import de.hdbw.webshop.service.user.UnregisteredUserService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

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
        unregisteredUserService.createNewUnregisteredUser(httpsessionEvent);
    }
}
