package de.hdbw.webshop.service.artwork;

import de.hdbw.webshop.dto.artwork.EditMyArtworkDTO;
import de.hdbw.webshop.exception.exceptions.ImageNotFoundException;
import de.hdbw.webshop.model.artwork.CustomMultipartFile;
import de.hdbw.webshop.model.artwork.entity.ArtworkEntity;
import de.hdbw.webshop.model.artwork.entity.ImageEntity;
import de.hdbw.webshop.model.artwork.entity.ImageMultipartWrapper;
import de.hdbw.webshop.repository.artwork.ImageRepository;
import lombok.extern.apachecommons.CommonsLog;
import one.util.streamex.EntryStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.Transient;
import java.io.*;
import java.util.List;

import java.util.stream.Collectors;


@Service
@CommonsLog
public class ImageService {

    private final ImageRepository imageRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public ImageEntity save(ImageEntity image) throws NullPointerException {
        if (image == null)
            throw new NullPointerException("Image Data NULL");
        return imageRepository.save(image);
    }

    public MultipartFile getMultipartByImageEntity(ImageEntity image) {
        return new CustomMultipartFile(image.getData(), image.getUuid(), image.getFileType());
    }

    public ImageEntity findImageByUuid(String uuid) {
        return imageRepository.findByUuid(uuid).orElseThrow(
                ImageNotFoundException::new
        );
    }

    public ImageEntity findImageByArtworkAndPosition(long artworkId, int position) {
        return imageRepository.findByArtworkIdAfterAndPosition(artworkId, position).orElseThrow(
                ImageNotFoundException::new
        );
    }

    @Transient
    public ImageEntity getLocalImage(String path, String fileType) throws Exception {
        InputStream is = getResourceFileAsInputStream(path);
        byte[] bdata = FileCopyUtils.copyToByteArray(is);
        ImageEntity image = new ImageEntity(fileType, 0, null, bdata);
        return image;
    }

    public ArtworkEntity changeImagesIfNecessary(EditMyArtworkDTO artworkChanges, ArtworkEntity existingArtwork) {
        List<ImageMultipartWrapper> newImageAsMultipartWrapper = List.of(
                artworkChanges.getFirstImage(), artworkChanges.getSecondImage(),
                artworkChanges.getThirdImage(), artworkChanges.getForthImage());
        List<ImageMultipartWrapper> newImageAsMultipartWrapperWithoutEmpty = newImageAsMultipartWrapper.stream()
                .filter(image -> image.getMultipartFile().getSize()!=0).collect(Collectors.toList());
        newImageAsMultipartWrapperWithoutEmpty.stream().forEach(
                image -> addNewImageToArtwork(existingArtwork, image.getMultipartFile(), image.getPosition()-1)
        );
        return existingArtwork;
    }

    ArtworkEntity addNewImageToArtwork(ArtworkEntity existingArtwork, MultipartFile newImage, int index) {
        if (existingArtwork.getImages().size() > index) {
            ImageEntity oldImage = existingArtwork.getImages().get(index);
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
        existingArtwork.addImage(ImageEntity.buildImage(newImage, index + 1, existingArtwork));
        return existingArtwork;
    }


    private static InputStream getResourceFileAsInputStream(String fileName) {
        ClassLoader classLoader = ImageEntity.class.getClassLoader();
        return classLoader.getResourceAsStream(fileName);
    }

    public List<String> findAllImageUuidsByArtworkAndOrderByPosition(long id) {
        return imageRepository.findAllImageUuidsByArtworkAndOrderByPosition(id);
    }

    public List<MultipartFile> getMultipartfilesByImageEntities(List<ImageEntity> images) {
        List<MultipartFile> multipartFiles = images.stream().map(this::getMultipartByImageEntity).collect(Collectors.toList());
        while(multipartFiles.size()!=4) {
            try {
                multipartFiles.add(getMultipartByImageEntity(getLocalImage("static/images/upload_image.jpg", "image/jpg")));
            } catch (Exception e) {
                throw new ImageNotFoundException();
            }
        }
        return multipartFiles;
    }

    public List<ImageEntity> getImageEntitiesByEditMyArtworkDTO(EditMyArtworkDTO editMyArtworkDTO, ArtworkEntity artworkEntity) {
        EntryStream.of(editMyArtworkDTO.getImages()).forKeyValue(
                (index, image) -> image.setPosition(index+1)
        );
        return editMyArtworkDTO.getImages()
                .stream().filter(
                imageMultipartWrapper -> imageMultipartWrapper.getMultipartFile().getSize()!=0
        ).collect(Collectors.toList())
                .stream().map(
                        imageMultipartWrapper -> ImageEntity.buildImage(
                                imageMultipartWrapper.getMultipartFile(), imageMultipartWrapper.getPosition(), artworkEntity)
                ).collect(Collectors.toList());
    }
}