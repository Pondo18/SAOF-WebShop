package de.hdbw.webshop.exception;

public final class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(final String message) {
        super(message);
    }
}
