package de.hdbw.webshop.security;

import de.hdbw.webshop.service.artwork.ShoppingCartService;
import de.hdbw.webshop.service.session.RedirectService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyAuthenticationSuccessHandler
        extends SimpleUrlAuthenticationSuccessHandler
        implements AuthenticationSuccessHandler {

    private RequestCache requestCache = new HttpSessionRequestCache();
    private RedirectService redirectService;
    private ShoppingCartService shoppingCartService;

    public MyAuthenticationSuccessHandler() {
        super();
        setUseReferer(true);
    }

    public MyAuthenticationSuccessHandler(RedirectService redirectService, ShoppingCartService shoppingCartService) {
        this.redirectService = redirectService;
        this.shoppingCartService = shoppingCartService;
        setUseReferer(true);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        SavedRequest savedRequest = this.requestCache.getRequest(request, response);
        if (savedRequest == null) {
            super.onAuthenticationSuccess(request, response, authentication);
        } else {
            String targetUrlParameter = this.getTargetUrlParameter();
            if (!this.isAlwaysUseDefaultTargetUrl() && (targetUrlParameter == null || !StringUtils.hasText(request.getParameter(targetUrlParameter)))) {
                this.clearAuthenticationAttributes(request);
                String targetUrl = redirectService.getRedirectPath(savedRequest.getRedirectUrl());
                String jsessionid = request.getParameter("jsessionid");
                if (jsessionid!=null) {
                    shoppingCartService.changeUserForShoppingCartAndSave(jsessionid, authentication);
                }
                this.getRedirectStrategy().sendRedirect(request, response, targetUrl);
            } else {
                this.requestCache.removeRequest(request, response);
                super.onAuthenticationSuccess(request, response, authentication);
            }
        }
    }
}