package de.hdbw.webshop.primary.service;

import de.hdbw.webshop.primary.model.ArtworkEntity;
import de.hdbw.webshop.primary.repository.ArtworkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtworkService {

    private final ArtworkRepository artworkRepository;

    @Autowired
    public ArtworkService(ArtworkRepository artworkRepository) {
        this.artworkRepository = artworkRepository;
    }

    public List<ArtworkEntity> getAllArtworks() {
        return artworkRepository.findAll();
    }
}
