package de.hdbw.webshop.service.cookie;

import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class CookieService {

    public Cookie createNewAnonymousCookie(HttpServletResponse response) {
        String randomUUID = UUID.randomUUID().toString();
        Cookie cookie = new Cookie("session", randomUUID);
        cookie.setMaxAge(20160);
        response.addCookie(cookie);
        return cookie;
    }

    public boolean cookieDoesExist(HttpServletRequest request, String cookieName) {
        if (request.getCookies() == null) {
            return false;
        }
        List<Cookie> cookies = Arrays.asList(request.getCookies());
        return cookies.stream().anyMatch(
                cookie -> cookie.getName().equals(cookieName)
        );
    }
}
