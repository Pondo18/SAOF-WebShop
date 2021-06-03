package de.hdbw.webshop.util.string;
import de.hdbw.webshop.repository.artwork.ArtworkRepository;
import de.hdbw.webshop.service.artwork.image.ImageService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static java.lang.Math.abs;
import static java.lang.Math.random;

@Component
public class NameHelper {

    final ImageService imageService;
    final ArtworkRepository artworkRepository;

    public NameHelper(@Lazy ImageService imageService, ArtworkRepository artworkRepository) {
        this.imageService = imageService;
        this.artworkRepository = artworkRepository;
    }

    public String generateArtworkName(String artworkName) {
        artworkName = artworkName.replace(" ", "_").toLowerCase();
        while (artworkWithNameAlreadyExists(artworkName)) {
            artworkName = artworkName + "_" + randomNumber();
        }
        return artworkName;
    }

    private boolean artworkWithNameAlreadyExists(String generatedArtworkName) {
        return artworkRepository.existsByGeneratedArtworkName(generatedArtworkName);    }

    private String randomNumber() {
        int randomNumberFrom1To10 = (int) (random() *10);
        return String.valueOf(randomNumberFrom1To10);
    }

    public String getUnusedUuid() {
        String uuid = getUuid();
        while (imageService.uuidIsUsed(uuid)) {
            uuid = getUuid();
        }
        return uuid;
    }

    private String getUuid() {
        return UUID.randomUUID().toString();
    }
}