package de.hdbw.webshop.util.string;
import de.hdbw.webshop.repository.artwork.ArtworkRepository;
import org.springframework.stereotype.Component;

import static java.lang.Math.abs;
import static java.lang.Math.random;

@Component
public class NameHelper {

    final ArtworkRepository artworkRepository;

    public NameHelper(ArtworkRepository artworkRepository) {
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
}