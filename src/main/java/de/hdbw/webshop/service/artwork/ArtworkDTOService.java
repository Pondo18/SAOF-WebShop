package de.hdbw.webshop.service.artwork;

import de.hdbw.webshop.dto.artwork.ArtworkForDetailedViewDTO;
import de.hdbw.webshop.dto.artwork.ArtworkForListViewDTO;
import de.hdbw.webshop.dto.artwork.EditMyArtworkDTO;
import de.hdbw.webshop.exception.exceptions.ArtworkNotFoundException;
import de.hdbw.webshop.model.artwork.entity.ArtworkEntity;
import de.hdbw.webshop.model.artwork.entity.ImageEntity;
import de.hdbw.webshop.model.artwork.entity.ImageMultipartWrapper;
import de.hdbw.webshop.model.users.entity.ArtistEntity;
import de.hdbw.webshop.repository.artwork.ArtworkRepository;
import de.hdbw.webshop.util.string.NameHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static de.hdbw.webshop.dto.artwork.ArtworkForDetailedViewDTO.buildImageUrls;

@Service
public class ArtworkDTOService {

    private final ArtworkRepository artworkRepository;
    private final ImageService imageService;
    private final NameHelper nameHelper;
    @Value("${host.url}")
    private String host;

    @Autowired
    public ArtworkDTOService(ArtworkRepository artworkRepository, ImageService imageService, NameHelper nameHelper) {
        this.artworkRepository = artworkRepository;
        this.imageService = imageService;
        this.nameHelper = nameHelper;
    }

    public ArtworkForDetailedViewDTO getArtworkForDetailedInformationPage(String generatedArtworkName) {
        ArtworkEntity artworkEntity = artworkRepository.findByGeneratedArtworkName(generatedArtworkName).orElseThrow(
                ArtworkNotFoundException::new
        );
        ArtworkForDetailedViewDTO artwork = new ArtworkForDetailedViewDTO();
        artwork.build(artworkEntity);
        List<String> artworkImageUuids = imageService.findAllImageUuidsByArtworkAndOrderByPosition(artworkEntity.getId());
        List<String> artworkImageUrls = buildImageUrls(artworkImageUuids, host);
        artwork.setImagesUrl(artworkImageUrls);
        return artwork;
    }

    public List<ArtworkForListViewDTO> getAllArtworksForArtworksPage() {
        List<ArtworkEntity> allArtworkEntities = artworkRepository.findAllByAvailableGreaterThan(0);
        return getAllArtworksForListViewByArtworkEntity(allArtworkEntities);
    }

    public ArtworkForListViewDTO getArtworkForListViewByArtworkEntity(ArtworkEntity artworkEntity) {
        ArtworkForListViewDTO artworkForDetailedViewDTO = new ArtworkForListViewDTO();
        return artworkForDetailedViewDTO.build(artworkEntity, host);
    }

    public List<ArtworkForListViewDTO> getAllArtworksForListViewByArtworkEntity(List<ArtworkEntity> artworks) {
        return artworks.stream().map(this::getArtworkForListViewByArtworkEntity
        ).collect(Collectors.toList());
    }

    public ArtworkEntity getArtworkEntityByCreateNewArtworkDTO(EditMyArtworkDTO editMyArtworkDTO, ArtistEntity artist) {
        ArtworkEntity artworkEntity = new ArtworkEntity(
                editMyArtworkDTO.getArtworkName(), artist,
                editMyArtworkDTO.getArtworkDescription(),
                editMyArtworkDTO.getArtworkPrice());
        artworkEntity.setGeneratedArtworkName(nameHelper.generateArtworkName(artworkEntity.getArtworkName()));
        return setImagesForArtworkFromArtworkDTO(editMyArtworkDTO, artworkEntity);
    }

    public EditMyArtworkDTO getEditMyArtworkDTOByArtworkEntity(ArtworkEntity artworkEntity) {
        EditMyArtworkDTO editMyArtworkDTO = new EditMyArtworkDTO(
                artworkEntity.getArtworkName(),
                artworkEntity.getDescription(), artworkEntity.getPrice());
        return setImagesForEditMyArtworkDTOFromList(imageService.getMultipartfilesByImageEntities(artworkEntity.getImages()), editMyArtworkDTO);
    }

    public ArtworkEntity setImagesForArtworkFromArtworkDTO(EditMyArtworkDTO editMyArtworkDTO, ArtworkEntity artworkEntity) {
        List<ImageEntity> images = new ArrayList<>();
        if(editMyArtworkDTO.getFirstImage()!=null && editMyArtworkDTO.getFirstImage().getMultipartFile().getSize()!=0) {
            images.add(ImageEntity.buildImage(editMyArtworkDTO.getFirstImage().getMultipartFile(), 1, artworkEntity));
        }
        if(editMyArtworkDTO.getSecondImage()!=null && editMyArtworkDTO.getSecondImage().getMultipartFile().getSize()!=0) {
            images.add(ImageEntity.buildImage(editMyArtworkDTO.getSecondImage().getMultipartFile(), 2, artworkEntity));
        }
        if(editMyArtworkDTO.getThirdImage()!=null && editMyArtworkDTO.getThirdImage().getMultipartFile().getSize()!=0) {
            images.add(ImageEntity.buildImage(editMyArtworkDTO.getThirdImage().getMultipartFile(), 3, artworkEntity));
        }
        if(editMyArtworkDTO.getForthImage()!=null && editMyArtworkDTO.getForthImage().getMultipartFile().getSize()!=0) {
            images.add(ImageEntity.buildImage(editMyArtworkDTO.getForthImage().getMultipartFile(), 4, artworkEntity));
        }
        artworkEntity.setImages(images);
        return artworkEntity;
    }

    public EditMyArtworkDTO setImagesForEditMyArtworkDTOFromList(List<MultipartFile> images, EditMyArtworkDTO artworkDTO) {
        try {
            ImageMultipartWrapper imageMultipartWrapper = new ImageMultipartWrapper(images.get(0), 1);
            artworkDTO.setFirstImage(imageMultipartWrapper);
        } catch (IndexOutOfBoundsException e) {
        }
        try {
            artworkDTO.setSecondImage(new ImageMultipartWrapper(images.get(1), 2));
        } catch (IndexOutOfBoundsException e) {
        }
        try {
            artworkDTO.setThirdImage(new ImageMultipartWrapper(images.get(2), 3));
        } catch (IndexOutOfBoundsException e) {
        }
        try {
            artworkDTO.setForthImage(new ImageMultipartWrapper(images.get(3), 4));
        } catch (IndexOutOfBoundsException e) {
        }
        return artworkDTO;
    }
}