package de.hdbw.webshop.service.artwork.image;

import de.hdbw.webshop.dto.artwork.EditMyArtworkDTO;
import de.hdbw.webshop.exception.exceptions.ImageNotFoundException;
import de.hdbw.webshop.model.artwork.images.CustomMultipartFile;
import de.hdbw.webshop.model.artwork.artworks.entity.ArtworkEntity;
import de.hdbw.webshop.model.artwork.images.Image;
import de.hdbw.webshop.model.artwork.images.entity.BigSizedImageEntity;
import de.hdbw.webshop.model.artwork.images.entity.DefaultImageEntity;
import de.hdbw.webshop.model.artwork.images.ImageMultipartWrapper;
import de.hdbw.webshop.model.artwork.images.entity.MediumSizedImageEntity;
import de.hdbw.webshop.model.artwork.images.entity.SmallSizedImageEntity;
import de.hdbw.webshop.repository.artwork.image.DefaultImageRepository;
import lombok.extern.apachecommons.CommonsLog;
import one.util.streamex.EntryStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.Transient;
import java.io.*;
import java.util.List;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@CommonsLog
public class ImageService {

    private final DefaultImageRepository defaultImageRepository;
    private final SmallImageService smallImageService;
    private final MediumImageService mediumImageService;
    private final BigImageService bigImageService;

    @Autowired
    public ImageService(DefaultImageRepository defaultImageRepository, SmallImageService smallImageService, MediumImageService mediumImageService, BigImageService bigImageService) {
        this.defaultImageRepository = defaultImageRepository;
        this.smallImageService = smallImageService;
        this.mediumImageService = mediumImageService;
        this.bigImageService = bigImageService;
    }

    public DefaultImageEntity save(DefaultImageEntity image) throws NullPointerException {
        if (image == null)
            throw new NullPointerException("Image Data NULL");
        return defaultImageRepository.save(image);
    }

    public MultipartFile getMultipartByImage(Image image) {
        return new CustomMultipartFile(image.getData(), image.getUuid());
    }

    public ImageMultipartWrapper getImageMultipartWrapperByImage(Image image) {
        return new ImageMultipartWrapper(getMultipartByImage(image));
    }

    public Image getImageByUuid(String uuid, SizedImageService imageService) {
        try {
            return imageService.getByUuid(uuid);
        } catch(ImageNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Image not found",
                    e
            );
        }
    }

    public DefaultImageEntity findImageByArtworkAndPosition(long artworkId, int position) {
        return defaultImageRepository.findByArtworkIdAfterAndPosition(artworkId, position).orElseThrow(
                ImageNotFoundException::new
        );
    }

    @Transient
    public DefaultImageEntity getLocalImageInDefaultSize(String path, String fileType) throws Exception {
        InputStream is = getResourceFileAsInputStream(path);
        byte[] bdata = FileCopyUtils.copyToByteArray(is);
        DefaultImageEntity image = new DefaultImageEntity(fileType, 0, bdata);
        return image;
    }

    public ArtworkEntity changeImagesIfNecessary(EditMyArtworkDTO artworkChanges, ArtworkEntity existingArtwork) {
        EntryStream.of(artworkChanges.getImages()).forKeyValue(
                (index, image) -> image.setPosition(index + 1)
        );
        List<ImageMultipartWrapper> newImageAsMultipartWrapperWithoutEmpty = artworkChanges.getImages().stream()
                .filter(image -> image.getMultipartFile().getSize() != 0)
                .collect(Collectors.toList());
        newImageAsMultipartWrapperWithoutEmpty.forEach(
                image -> addNewImageToArtwork(existingArtwork, image.getMultipartFile(), image.getPosition() - 1)
        );
        return existingArtwork;
    }

    ArtworkEntity addNewImageToArtwork(ArtworkEntity existingArtwork, MultipartFile newImage, int index) {
        if (existingArtwork.getImages().size() > index) {
            DefaultImageEntity oldImage = existingArtwork.getImages().get(index);
            try {
                if (oldImage.getData() == newImage.getBytes()) {
                    return existingArtwork;
                }
            } catch (IOException e) {
                log.warn("Error changing the image with the position: " + index + 1 + " for the artwork: " + existingArtwork.getGeneratedArtworkName());
                throw new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Internal Error"
                );
            }
            existingArtwork.deleteImageByIndex(index);
        }
        existingArtwork.addImage(DefaultImageEntity.buildImage(newImage, index + 1, existingArtwork));
        return existingArtwork;
    }


    private static InputStream getResourceFileAsInputStream(String fileName) {
        ClassLoader classLoader = DefaultImageEntity.class.getClassLoader();
        return classLoader.getResourceAsStream(fileName);
    }

    public List<String> getAllBigImageUuidsByArtworkAndOrderByPosition(long artworkId) {
        return bigImageService.getAllBigImageUuidsByArtworkAndOrderByPosition(artworkId);
    }

    public List<ImageMultipartWrapper> getMultipartWrapperFilesByImageEntities(List<MediumSizedImageEntity> images) {
        List<ImageMultipartWrapper> imageMultipartWrappers = images.stream().map(
                this::getImageMultipartWrapperByImage
        ).collect(Collectors.toList());
        while (imageMultipartWrappers.size() != 4) {
            try {
                imageMultipartWrappers.add(getImageMultipartWrapperByImage(mediumImageService.getDefaultImage()));
            } catch (Exception e) {
                throw new ImageNotFoundException();
            }
        }
        return imageMultipartWrappers;
    }

    public void buildAndSetSizedImages(DefaultImageEntity defaultImageEntity) {
        SmallSizedImageEntity smallSizedImageEntity = smallImageService.buildSmallImageEntity(defaultImageEntity);
        MediumSizedImageEntity mediumSizedImageEntity = mediumImageService.buildMediumImageEntity(defaultImageEntity);
        BigSizedImageEntity bigSizedImageEntity = bigImageService.buildBigSizedImageEntity(defaultImageEntity);
        defaultImageEntity.setSmallSizedImageEntity(smallSizedImageEntity);
        defaultImageEntity.setMediumSizedImageEntity(mediumSizedImageEntity);
        defaultImageEntity.setBigSizedImageEntity(bigSizedImageEntity);
    }

    public List<DefaultImageEntity> getImageEntitiesByEditMyArtworkDTO(EditMyArtworkDTO editMyArtworkDTO, ArtworkEntity artworkEntity) {
        EntryStream.of(editMyArtworkDTO.getImages()).forKeyValue(
                (index, image) -> image.setPosition(index + 1)
        );
        return editMyArtworkDTO.getImages()
                .stream().filter(
                        imageMultipartWrapper -> imageMultipartWrapper.getMultipartFile().getSize() != 0
                ).collect(Collectors.toList())
                .stream().map(
                        imageMultipartWrapper -> {
                            DefaultImageEntity defaultImage = DefaultImageEntity.buildImage(
                                    imageMultipartWrapper.getMultipartFile(), imageMultipartWrapper.getPosition(), artworkEntity);
                            buildAndSetSizedImages(defaultImage);
                            return defaultImage;
                        }
                ).collect(Collectors.toList());
    }

    public List<SmallSizedImageEntity> getSmallSizedImageEntityByDefaultImages(List<DefaultImageEntity> defaultImages) {
        return smallImageService.getSmallSizedImageEntityByDefaultImages(defaultImages);
    }

    public List<MediumSizedImageEntity> getMediumSizedImageEntityByDefaultImages(List<DefaultImageEntity> defaultImages) {
        return mediumImageService.getMediumSizedImagesByDefaultImages(defaultImages);
    }

    public boolean uuidIsUsed(String uuid) {
        return (smallImageService.smallImageExists(uuid) ||
                mediumImageService.mediumImageExists(uuid) ||
                bigImageService.bigImageExists(uuid));
    }

    public ResponseEntity<byte[]> getResponseEntityForDefaultImage(SizedImageService imageService) {
        try {
            Image defaultImage = imageService.getDefaultImage();
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.valueOf(defaultImage.getFileType()))
                    .body(defaultImage.getData());
        } catch (Exception e) {
            log.warn("Could not resolve default image");
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Image not found",
                    e
            );
        }
    }

    public ResponseEntity<byte[]> getImageResponseEntity(String uuid, Image imageByUuid) {
        try {
            log.debug("Return small Image by uuid: '" + uuid + "'");
            return ResponseEntity.ok()
                    .contentType(MediaType.valueOf(imageByUuid.getFileType()))
                    .body(imageByUuid.getData());
        } catch (ImageNotFoundException imageNotFoundException) {
            log.warn("Could not return Image by Image UUID: '" + uuid + "'");
            return getResponseEntityForDefaultImage(smallImageService);
        }
    }

}