package de.hdbw.webshop.service.artwork;

import de.hdbw.webshop.exception.exceptions.ImageNotFoundException;
import de.hdbw.webshop.model.artwork.ArtworkEntity;
import de.hdbw.webshop.model.artwork.ImageEntity;
import de.hdbw.webshop.repository.artwork.ImageRepository;
import one.util.streamex.EntryStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Transient;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;


@Service
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

    public List<ImageEntity> getImagesByMultipartfiles(List<MultipartFile> imagesAsMultipart, ArtworkEntity artworkEntity){
        List<MultipartFile> images = imagesAsMultipart.stream().filter(image -> image.getSize()!=0).collect(Collectors.toList());
        return EntryStream.of(images).mapKeyValue(
                (index, multipartFile) -> ImageEntity.buildImage(multipartFile, index+1, artworkEntity)).collect(Collectors.toList()
        );
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

    private static InputStream getResourceFileAsInputStream(String fileName) {
        ClassLoader classLoader = ImageEntity.class.getClassLoader();
        return classLoader.getResourceAsStream(fileName);
    }

    public List<String> findAllImageUuidsByArtworkAndOrderByPosition(long id) {
        return imageRepository.findAllImageUuidsByArtworkAndOrderByPosition(id);
    }
}