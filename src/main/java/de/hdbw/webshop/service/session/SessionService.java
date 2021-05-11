package de.hdbw.webshop.service.session;

import lombok.extern.apachecommons.CommonsLog;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@CommonsLog
@Service
public class SessionService {

    private final AuthenticationManager authenticationManager;

    public SessionService(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public void doAutoLogin(String email, String password, HttpServletRequest request) {
        try {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email, password);
            token.setDetails(new WebAuthenticationDetails(request));
            Authentication authentication = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.debug("Login user with the email: " + email);
        } catch (Exception e) {
            log.warn("Couldn't Login user with the email: " + email + " After Registration");
            SecurityContextHolder.getContext().setAuthentication(null);
        }
    }
}

