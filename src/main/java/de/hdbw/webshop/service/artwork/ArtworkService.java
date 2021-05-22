package de.hdbw.webshop.service.artwork;

import de.hdbw.webshop.exception.exceptions.ArtworkNotFoundException;
import de.hdbw.webshop.model.artwork.ArtworkEntity;
import de.hdbw.webshop.model.users.entity.ArtistEntity;
import de.hdbw.webshop.repository.artwork.ArtworkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ArtworkService {

    private final ArtworkRepository artworkRepository;

    @Autowired
    public ArtworkService(ArtworkRepository artworkRepository) {
        this.artworkRepository = artworkRepository;
    }

    public long getArtworkIdByArtworkName(String artworkName) {
        return artworkRepository.findArtworkIdByGeneratedArtworkName(artworkName).orElseThrow(
                ArtworkNotFoundException::new
        );
    }

    public ArtworkEntity getArtworkEntityByGeneratedArtworkName (String generatedArtworkName) {
        return artworkRepository.findByGeneratedArtworkName(generatedArtworkName).orElseThrow(
                ArtworkNotFoundException::new
        );
    }

    public List<ArtworkEntity> changeAmountOfAvailableArtworks(List<ArtworkEntity> artworksToChange) {
        return artworksToChange.stream().peek(artworkEntity -> {
            artworkEntity.setAvailable(artworkEntity.getAvailable()-1);
        }).collect(Collectors.toList());
    }

    public void saveAll(List<ArtworkEntity> artworksToSave) {
        artworkRepository.saveAll(artworksToSave);
    }

    public List<ArtworkEntity> findAllArtworksByArtist(ArtistEntity artistEntity) {
        return artworkRepository.findAllByArtist(artistEntity);
    }
}
