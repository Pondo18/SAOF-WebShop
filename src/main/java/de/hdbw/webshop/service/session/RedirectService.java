package de.hdbw.webshop.service.session;

import de.hdbw.webshop.util.string.UrlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RedirectService {

    private final UrlUtil urlUtil;

    @Autowired
    public RedirectService(UrlUtil urlUtil) {
        this.urlUtil = urlUtil;
    }

    public String getRedirectUrl(String refererUrl) {
        String refererPath = urlUtil.getPathByUrl(refererUrl);
        return refererPath.equals("/checkout") ?
                urlUtil.getUrlByPath("/shopping_cart") :  urlUtil.getUrlByPath(refererPath);
    }

    public String getRedirectPath(String refererUrl) {
        return urlUtil.getPathByUrl(refererUrl);
    }

    public String getViewNameByRequestUrl(String requestUrl) {
        return urlUtil.convertSnakeCaseToCamelCase(requestUrl);
    }
}
