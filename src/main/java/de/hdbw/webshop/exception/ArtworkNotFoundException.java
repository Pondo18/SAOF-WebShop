package de.hdbw.webshop.exception;

public class ArtworkNotFoundException extends RuntimeException {
    public ArtworkNotFoundException (final String message) {
        super(message);
    }
}
