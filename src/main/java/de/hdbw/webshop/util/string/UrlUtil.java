package de.hdbw.webshop.util.string;

import org.springframework.stereotype.Component;

@Component
public class UrlUtil {

    private final String host = "http://localhost:8080/";

    public String getPathByUrl(String entireUrl) {
        return entireUrl.replace(host, "");
    }

    public String getUrlByPath(String path) {
        return host + path;
    }

    public String convertSnakeCaseToCamelCase(String string) {
        string = string.substring(0, 1).toUpperCase()
                + string.substring(1);
        while (string.contains("_")) {
            string = string
                    .replaceFirst("_[a-z]",
                            String.valueOf(Character.toUpperCase(string.charAt(string.indexOf("_") + 1))));
        }
        return string;
    }
}
