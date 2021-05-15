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

    public String getRedirectPath(String refererUrl) {
        String refererPath = urlUtil.getPathByUrl(refererUrl);
        return refererPath.equals("/checkout") ?
                urlUtil.getUrlByPath("/shopping_cart") :  urlUtil.getUrlByPath(refererPath);
    }

    public void test() {
        System.out.println("test");
    }
}
