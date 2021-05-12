package de.hdbw.webshop.service.artwork;

import de.hdbw.webshop.dto.ArtworkForDetailedViewDTO;
import de.hdbw.webshop.dto.ArtworkForListViewDTO;
import de.hdbw.webshop.exception.exceptions.ArtworkNotFoundException;
import de.hdbw.webshop.model.artwork.ArtworkEntity;
import de.hdbw.webshop.repository.artwork.ArtworkRepository;
import de.hdbw.webshop.repository.artwork.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static de.hdbw.webshop.dto.ArtworkForDetailedViewDTO.buildImageUrls;

@Service
public class ArtworkDTOService {

    private final ArtworkRepository artworkRepository;
    private final ImageRepository imageRepository;

    @Autowired
    public ArtworkDTOService(ArtworkRepository artworkRepository, ImageRepository imageRepository) {
        this.artworkRepository = artworkRepository;
        this.imageRepository = imageRepository;
    }

    public ArtworkForDetailedViewDTO getArtworkForDetailedInformationPage(String generatedArtworkName) {
        ArtworkEntity artworkEntity = artworkRepository.findByGeneratedArtworkName(generatedArtworkName).orElseThrow(
                () -> new ArtworkNotFoundException()
        );
        ArtworkForDetailedViewDTO artwork = new ArtworkForDetailedViewDTO();
        artwork.build(artworkEntity);
        List<String> artworkImageUuids = imageRepository.findAllImageUuidsByArtworkAndOrderByPosition(artworkEntity.getId());
        List<String> artworkImageUrls = buildImageUrls(artworkImageUuids);
        artwork.setImagesUrl(artworkImageUrls);
        return artwork;
    }

    public List<ArtworkForListViewDTO> getAllArtworksForArtworksPage() {
        List<ArtworkEntity> allArtworkEntities = artworkRepository.findAll();
        List<ArtworkForListViewDTO> artworksForListView = allArtworkEntities.stream().map(artwork ->
                getArtworkForListViewByArtworkEntity(artwork)
        ).collect(Collectors.toList());
        return artworksForListView;
    }

    public ArtworkForListViewDTO getArtworkForListViewByArtworkEntity(ArtworkEntity artworkEntity) {
        ArtworkForListViewDTO artworkForDetailedViewDTO = new ArtworkForListViewDTO();
        return artworkForDetailedViewDTO.build(artworkEntity);
    }
}
