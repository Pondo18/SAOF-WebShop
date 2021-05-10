package de.hdbw.webshop.service.cookie;

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
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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
        } catch (Exception e) {
            SecurityContextHolder.getContext().setAuthentication(null);
        }
    }

    public Cookie createNewSessionCookie(HttpServletRequest request, HttpServletResponse response) {
        if (!sessionCookieAlreadyExists(request)) {
            String randomUUID = UUID.randomUUID().toString();
            Cookie cookie = new Cookie("JSESSIONID", randomUUID);
            response.addCookie(cookie);

        }
        return null;
    }

    public boolean sessionCookieAlreadyExists(HttpServletRequest request) {
        List<Cookie> cookies = Arrays.asList(request.getCookies());
        return cookies.stream().anyMatch(cookie -> cookie.getName().equals("JSESSIONID"));
    }
}
