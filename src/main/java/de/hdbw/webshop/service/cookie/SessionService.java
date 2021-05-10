package de.hdbw.webshop.service.cookie;

import de.hdbw.webshop.service.user.UnregisteredUserService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;

@CommonsLog
@Service
public class SessionService {

    private final CookieService cookieService;
    private final AuthenticationManager authenticationManager;
    private final UnregisteredUserService unregisteredUserService;

    public SessionService(CookieService cookieService, AuthenticationManager authenticationManager, UnregisteredUserService unregisteredUserService) {
        this.cookieService = cookieService;
        this.authenticationManager = authenticationManager;
        this.unregisteredUserService = unregisteredUserService;
    }

    public void doAutoLogin(String email, String password, HttpServletRequest request) {
        try {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email, password);
            token.setDetails(new WebAuthenticationDetails(request));
            Authentication authentication = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            SecurityContextHolder.getContext().setAuthentication(null);
        }
    }

    public void createSessionIfNotExisting (HttpServletRequest request, HttpServletResponse response) {
        if (!(cookieService.cookieDoesExist(request, "session")
                || cookieService.cookieDoesExist(request, "JSESSIONID"))) {
            Cookie cookie = cookieService.createNewAnonymousCookie(response);
            LocalDate currentDate = LocalDate.now();
            LocalDate expiringDate = currentDate.plusDays(cookie.getMaxAge()/1440);
            unregisteredUserService.createNewUnregisteredUser(cookie.getValue(), expiringDate);
        }
    }
}
