package de.hdbw.webshop.service.authentication;

import de.hdbw.webshop.security.MySessionRegistry;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@CommonsLog
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final MySessionRegistry mySessionRegistry;

    public AuthenticationService(AuthenticationManager authenticationManager, MySessionRegistry mySessionRegistry) {
        this.authenticationManager = authenticationManager;
        this.mySessionRegistry = mySessionRegistry;
    }

    public void doAutoLogin(String email, String password, HttpServletRequest request, HttpServletResponse response) {
        try {
            invalidateSessionIfSessionExists(request, response);
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

    public void invalidateSessionIfSessionExists(HttpServletRequest request, HttpServletResponse response) {
//        new SecurityContextLogoutHandler().logout(request, null, null);
        try {
            request.logout();
            Cookie[] cookies = request.getCookies();
            if (cookies!=null) {
                for (Cookie cookie : cookies) {
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
//            request.getSession(false).invalidate();
//            SecurityContextHolder.clearContext();
        } catch (ServletException e) {
            e.printStackTrace();
        }
//        SessionInformation sessionInformation = mySessionRegistry.getSessionInformation(request.getSession(false).getId());
//        sessionInformation.expireNow();
    }
}
