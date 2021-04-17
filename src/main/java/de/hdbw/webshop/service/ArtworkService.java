package de.hdbw.webshop.service;

import de.hdbw.webshop.model.ArtworkEntity;
import de.hdbw.webshop.repository.ArtworkRepository;
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
