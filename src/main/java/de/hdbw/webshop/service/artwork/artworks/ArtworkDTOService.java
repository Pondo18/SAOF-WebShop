package de.hdbw.webshop.service.artwork.artworks;

import de.hdbw.webshop.dto.artwork.ArtworkForDetailedViewDTO;
import de.hdbw.webshop.dto.artwork.ArtworkForListViewDTO;
import de.hdbw.webshop.dto.artwork.EditMyArtworkDTO;
import de.hdbw.webshop.exception.exceptions.ArtworkNotFoundException;
import de.hdbw.webshop.exception.exceptions.ImageNotFoundException;
import de.hdbw.webshop.model.artwork.artworks.entity.ArtworkEntity;
import de.hdbw.webshop.model.users.entity.ArtistEntity;
import de.hdbw.webshop.repository.artwork.ArtworkRepository;
import de.hdbw.webshop.service.artwork.image.ImageService;
import de.hdbw.webshop.util.language.PriceUtil;
import de.hdbw.webshop.util.string.NameHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static de.hdbw.webshop.dto.artwork.ArtworkForDetailedViewDTO.buildImageUrls;

@Service
public class ArtworkDTOService {

    private final ArtworkRepository artworkRepository;
    private final ImageService imageService;
    private final NameHelper nameHelper;
    private final PriceUtil priceUtil;
    @Value("${host.url}")
    private String host;

    @Autowired
    public ArtworkDTOService(ArtworkRepository artworkRepository, ImageService imageService, NameHelper nameHelper, PriceUtil priceUtil) {
        this.artworkRepository = artworkRepository;
        this.imageService = imageService;
        this.nameHelper = nameHelper;
        this.priceUtil = priceUtil;
    }

    public ArtworkForDetailedViewDTO getArtworkForDetailedInformationPage(String generatedArtworkName) {
        ArtworkEntity artworkEntity = artworkRepository.findByGeneratedArtworkName(generatedArtworkName).orElseThrow(
                ArtworkNotFoundException::new
        );
        ArtworkForDetailedViewDTO artwork = new ArtworkForDetailedViewDTO();
        artwork.build(artworkEntity, priceUtil.getPriceInCorrectCurrencyInputIsInEUR(artwork.getPrice()));
        List<String> bigArtworkImageUuids = imageService.getAllBigImageUuidsByArtworkAndOrderByPosition(artworkEntity.getId());
        List<String> artworkImageUrls = buildImageUrls(bigArtworkImageUuids, host);
        artwork.setImagesUrl(artworkImageUrls);
        return artwork;
    }

    public List<ArtworkForListViewDTO> getAllArtworksForArtworksPage() {
        List<ArtworkEntity> allArtworkEntities = artworkRepository.findAllByAvailableGreaterThan(0);
        return getAllArtworksForListViewByArtworkEntity(allArtworkEntities);
    }

    public ArtworkForListViewDTO getArtworkForListViewByArtworkEntity(ArtworkEntity artworkEntity) {
        ArtworkForListViewDTO artworkForDetailedViewDTO = new ArtworkForListViewDTO();
        String primaryImageUuid = "noImageExisting";
        try {
            primaryImageUuid = getSmallPrimaryImageUuid(artworkEntity);
        } catch (Exception e) {
        }
        return artworkForDetailedViewDTO.build(
                artworkEntity, host, primaryImageUuid,
                priceUtil.getPriceInCorrectCurrencyInputIsInEUR(artworkEntity.getPrice()));
    }

    public List<ArtworkForListViewDTO> getAllArtworksForListViewByArtworkEntity(List<ArtworkEntity> artworks) {
        return artworks.stream().map(this::getArtworkForListViewByArtworkEntity
        ).collect(Collectors.toList());
    }

    public ArtworkEntity getArtworkEntityByCreateNewArtworkDTO(EditMyArtworkDTO editMyArtworkDTO, ArtistEntity artist) {
        ArtworkEntity artworkEntity = new ArtworkEntity(
                editMyArtworkDTO.getArtworkName(), artist,
                editMyArtworkDTO.getArtworkDescription(),
                priceUtil.getPriceInEURInputIsUnknown(editMyArtworkDTO.getArtworkPrice()));
        artworkEntity.setGeneratedArtworkName(nameHelper.generateArtworkName(artworkEntity.getArtworkName()));
        artworkEntity.setImages(imageService.getImageEntitiesByEditMyArtworkDTO(editMyArtworkDTO, artworkEntity));
        return artworkEntity;
    }

    public EditMyArtworkDTO getEditMyArtworkDTOByArtworkEntity(ArtworkEntity artworkEntity) {
        return new EditMyArtworkDTO(
                artworkEntity.getArtworkName(),
                artworkEntity.getDescription(), priceUtil.getPriceInCorrectCurrencyInputIsInEUR(artworkEntity.getPrice()),
                imageService.getMultipartWrapperFilesByImageEntities(imageService.getMediumSizedImageEntityByDefaultImages(artworkEntity.getImages())));
    }

    public String getSmallPrimaryImageUuid(ArtworkEntity artworkEntity) {
        return String.valueOf(artworkEntity.getImages().stream().findFirst().orElseThrow(
                ImageNotFoundException::new
        ).getSmallSizedImageEntity().getUuid());

    }
}