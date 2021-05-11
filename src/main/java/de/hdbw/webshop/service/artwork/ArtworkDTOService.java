package de.hdbw.webshop.service.artwork;

import de.hdbw.webshop.dto.ArtworkForArtworkInformationPageDTO;
import de.hdbw.webshop.dto.ArtworkForArtworksPageDTO;
import de.hdbw.webshop.exception.exceptions.ArtworkNotFoundException;
import de.hdbw.webshop.model.artwork.ArtworkEntity;
import de.hdbw.webshop.repository.artwork.ArtworkRepository;
import de.hdbw.webshop.repository.artwork.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static de.hdbw.webshop.dto.ArtworkForArtworkInformationPageDTO.buildImageUrls;

@Service
public class ArtworkDTOService {

    private final ArtworkRepository artworkRepository;
    private final ImageRepository imageRepository;

    @Autowired
    public ArtworkDTOService(ArtworkRepository artworkRepository, ImageRepository imageRepository) {
        this.artworkRepository = artworkRepository;
        this.imageRepository = imageRepository;
    }

    public ArtworkForArtworkInformationPageDTO getArtworkForDetailedInformationPage(String generatedArtworkName) {
        ArtworkEntity artworkEntity = artworkRepository.findByGeneratedArtworkName(generatedArtworkName).orElseThrow(
                () -> new ArtworkNotFoundException()
        );
        ArtworkForArtworkInformationPageDTO artwork = new ArtworkForArtworkInformationPageDTO();
        artwork.build(artworkEntity);
        List<String> artworkImageUuids = imageRepository.findAllImageUuidsByArtworkAndOrderByPosition(artworkEntity.getId());
        List<String> artworkImageUrls = buildImageUrls(artworkImageUuids);
        artwork.setImagesUrl(artworkImageUrls);
        return artwork;
    }

    public List<ArtworkForArtworksPageDTO> getAllArtworksForArtworksPage() {
        List<ArtworkEntity> allArtworkEntities = artworkRepository.findAll();
        List<ArtworkForArtworksPageDTO> artworksForArtworksPageDTOS = new ArrayList<>();
        for (ArtworkEntity artworkEntity : allArtworkEntities) {
            ArtworkForArtworksPageDTO artworkForArtworksPageDTO = new ArtworkForArtworksPageDTO();
            artworkForArtworksPageDTO.build(artworkEntity);
            artworksForArtworksPageDTOS.add(artworkForArtworksPageDTO);
        }
        return artworksForArtworksPageDTOS;
    }
}
