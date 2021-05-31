package de.hdbw.webshop.security;

import de.hdbw.webshop.service.user.ShoppingCartService;
import de.hdbw.webshop.service.session.RedirectHelper;
import de.hdbw.webshop.util.string.UrlUtil;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.util.StringUtils;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@CommonsLog
public class MyAuthenticationSuccessHandler
        extends SimpleUrlAuthenticationSuccessHandler
        implements AuthenticationSuccessHandler {

    private RequestCache requestCache = new HttpSessionRequestCache();
    private RedirectHelper redirectHelper;
    private ShoppingCartService shoppingCartService;
    private UrlUtil urlUtil;

    public MyAuthenticationSuccessHandler() {
        super();
        setUseReferer(true);
    }

    public MyAuthenticationSuccessHandler(RedirectHelper redirectHelper, ShoppingCartService shoppingCartService, UrlUtil urlUtil) {
        this.redirectHelper = redirectHelper;
        this.shoppingCartService = shoppingCartService;
        this.urlUtil = urlUtil;
        setUseReferer(true);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        String jsessionid = request.getParameter("jsessionid");
        if (jsessionid!=null) {
            log.debug("Changing shoppingCart from unregistered user: " + jsessionid +" to registered user");
            shoppingCartService.changeUserForShoppingCartAndSave(jsessionid, authentication);
        }
        String referer = request.getParameter("returnUrl");
        if (referer == null || urlUtil.getPathByUrl(referer).equals("/login")) {
            clearAuthenticationAttributes(request);
            this.getRedirectStrategy().sendRedirect(request, response, "/artworks");
        } else {
            String targetUrlParameter = this.getTargetUrlParameter();
            if (!this.isAlwaysUseDefaultTargetUrl() && (targetUrlParameter == null || !StringUtils.hasText(request.getParameter(targetUrlParameter)))) {
                this.clearAuthenticationAttributes(request);
                this.getRedirectStrategy().sendRedirect(request, response, redirectHelper.getRedirectUrl(referer));
            } else {
                this.requestCache.removeRequest(request, response);
                super.onAuthenticationSuccess(request, response, authentication);
            }
        }
    }
}