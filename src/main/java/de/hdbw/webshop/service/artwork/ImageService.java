package de.hdbw.webshop.service.artwork;

import de.hdbw.webshop.dto.artwork.EditMyArtworkDTO;
import de.hdbw.webshop.exception.exceptions.ImageNotFoundException;
import de.hdbw.webshop.model.artwork.CustomMultipartFile;
import de.hdbw.webshop.model.artwork.entity.ArtworkEntity;
import de.hdbw.webshop.model.artwork.entity.ImageEntity;
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
import java.util.Map;
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

    public List<ImageEntity> getImagesByMultipartfiles(List<MultipartFile> imagesAsMultipart, ArtworkEntity artworkEntity) {
        List<MultipartFile> images = imagesAsMultipart.stream().filter(image -> image.getSize() != 0).collect(Collectors.toList());
        return EntryStream.of(images).mapKeyValue(
                (index, multipartFile) -> ImageEntity.buildImage(multipartFile, index + 1, artworkEntity)).collect(Collectors.toList()
        );
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
    public ImageEntity getDefaultImage() throws Exception {
        InputStream is = getResourceFileAsInputStream("static/images/image_missing.png");
        String fileType = "image/png";
        byte[] bdata = FileCopyUtils.copyToByteArray(is);
        ImageEntity image = new ImageEntity(fileType, 0, null, bdata);
        return image;
    }

//    public String getBase64FromImage(String imagePath) {
//        File originalFile = new File("signature.jpg");
//        String encodedBase64 = null;
//        try {
//            FileInputStream fileInputStreamReader = new FileInputStream(originalFile);
//            byte[] bytes = new byte[(int)originalFile.length()];
//            fileInputStreamReader.read(bytes);
//            encodedBase64 = new String(Base64.encodeBase64(bytes));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        "static/images/upload_image.jpg"
//    }

    public ArtworkEntity changeImagesIfNecessary(EditMyArtworkDTO artworkChanges, ArtworkEntity existingArtwork) {
        Map<Integer, MultipartFile> newImagesMapAsMultipart = Map.of(
                0, artworkChanges.getFirstImage(),
                1, artworkChanges.getSecondImage(),
                2, artworkChanges.getThirdImage(),
                3, artworkChanges.getForthImage());
        List<Map.Entry<Integer, MultipartFile>> newImagesWithoutEmptyOnes = newImagesMapAsMultipart.entrySet().stream()
                .filter(entry -> entry.getValue().getSize() != 0).collect(Collectors.toList());
        newImagesWithoutEmptyOnes.stream().forEach(
              entry -> addNewImageToArtwork(existingArtwork, entry.getValue(), entry.getKey())
        );
        return existingArtwork;
//        List<MultipartFile> newImagesAsMultipart = List.of(
//                artworkChanges.getFirstImage(), artworkChanges.getSecondImage(),
//                artworkChanges.getThirdImage(), artworkChanges.getForthImage());
//        List<MultipartFile> newImagesAsMultipartWithoutEmptyOnes = newImagesAsMultipart.stream().filter(
//                multipartFile -> multipartFile.getSize() != 0
//        ).collect(Collectors.toList());
////        List<ImageEntity> newImages = EntryStream.of(newImagesAsMultipartWithoutEmptyOnes).mapKeyValue(
////                (index, multipartImage) ->
////                        addNewImageToArtwork(existingArtwork, multipartImage, index)
////        ).collect(Collectors.toList());
////        List<ImageEntity> oldImages = existingArtwork.getImages();
////        oldImages.clear();
////        newImages.stream().forEach(imageEntity -> existingArtwork.addImage(imageEntity));
//        EntryStream.of(newImagesAsMultipartWithoutEmptyOnes).forKeyValue(
//                (index, multipartImage) -> addNewImageToArtwork(existingArtwork, multipartImage, index)
//        );
//        return existingArtwork;
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

//    public long deleteImageByUuid(String uuid) {
//        return imageRepository.deleteAllByArtwork(artworkEntity);
//    }


    private static InputStream getResourceFileAsInputStream(String fileName) {
        ClassLoader classLoader = ImageEntity.class.getClassLoader();
        return classLoader.getResourceAsStream(fileName);
    }

    public List<String> findAllImageUuidsByArtworkAndOrderByPosition(long id) {
        return imageRepository.findAllImageUuidsByArtworkAndOrderByPosition(id);
    }

    public List<MultipartFile> getMultipartfilesByImageEntities(List<ImageEntity> images) {
        return images.stream().map(image -> getMultipartByImageEntity(image)).collect(Collectors.toList());
    }
}