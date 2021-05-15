package de.hdbw.webshop.util.string;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
public class UrlUtil {

    private final String host = "http://localhost:8080";

    public String getPathByUrl(String entireUrl) {
        return entireUrl.replace(host, "");
    }

    public String getUrlByPath(String path) {
        return host + path;
    }
}
