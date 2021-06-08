package de.hdbw.webshop.service.authentication;

import lombok.extern.apachecommons.CommonsLog;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
@CommonsLog
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public void doAutoLoginAfterUserRegistration(String email, String password, HttpServletRequest request, HttpServletResponse response) {
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
        try {
            request.logout();
            Cookie[] cookies = request.getCookies();
            if (cookies!=null) {
                for (Cookie cookie : cookies) {
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }


    public void authenticateArtistAfterRegistration(Object principal) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, null,
                AuthorityUtils.createAuthorityList("ROLE_ARTIST"));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
