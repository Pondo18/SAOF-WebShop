package de.hdbw.webshop.service;

import de.hdbw.webshop.exception.ArtworkNotFoundException;
import de.hdbw.webshop.model.artwork.ArtworkEntity;
import de.hdbw.webshop.model.artwork.ImageEntity;
import de.hdbw.webshop.repository.ArtworkRepository;
import de.hdbw.webshop.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArtworkService {

    private final ArtworkRepository artworkRepository;
    private final ImageRepository imageRepository;

    @Autowired
    public ArtworkService(ArtworkRepository artworkRepository, ImageRepository imageRepository) {
        this.artworkRepository = artworkRepository;
        this.imageRepository = imageRepository;
    }

    public List<ArtworkEntity> getAllArtworks() {
        return artworkRepository.findAll();
    }

    public ArtworkEntity getArtworkById(long artworkId) {
        ArtworkEntity artworkEntity = artworkRepository.findById(artworkId).orElseThrow(() -> new ArtworkNotFoundException(
                "Artwork by Id " +
                artworkId +
                " was not found"));
        artworkEntity.setImages(imageRepository.findAllByArtwork(artworkEntity));
        return artworkEntity;
    }
}
