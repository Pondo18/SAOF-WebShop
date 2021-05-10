package de.hdbw.webshop.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
public class ErrorPayload {

    private final String message;
    private final int statusCode;
    private final ZonedDateTime timestamp;
    private final String path;


    public ErrorPayload(String message, int statusCode, String path) {
        this.message = message;
        this.statusCode = statusCode;
        this.path = path;
        this.timestamp = ZonedDateTime.now();
    }
}
