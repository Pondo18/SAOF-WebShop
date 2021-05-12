package de.hdbw.webshop.service.artwork;

import de.hdbw.webshop.exception.exceptions.ArtworkNotFoundException;
import de.hdbw.webshop.model.artwork.ArtworkEntity;
import de.hdbw.webshop.repository.artwork.ArtworkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ArtworkService {

    private final ArtworkRepository artworkRepository;

    @Autowired
    public ArtworkService(ArtworkRepository artworkRepository) {
        this.artworkRepository = artworkRepository;
    }

    public long getArtworkIdByArtworkName(String artworkName) {
        return artworkRepository.findArtworkIdByGeneratedArtworkName(artworkName).orElseThrow(
                () -> new ArtworkNotFoundException()
        );
    }

    public ArtworkEntity getArtworkEntityByGeneratedArtworkName (String generatedArtworkName) {
        return artworkRepository.findByGeneratedArtworkName(generatedArtworkName).orElseThrow(
                () -> new ArtworkNotFoundException()
        );
    }
}
