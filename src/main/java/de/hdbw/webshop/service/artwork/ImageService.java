package de.hdbw.webshop.service.artwork;

import de.hdbw.webshop.exception.exceptions.ImageNotFoundException;
import de.hdbw.webshop.model.artwork.ImageEntity;
import de.hdbw.webshop.repository.artwork.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import javax.persistence.Transient;
import java.io.InputStream;


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

    public ImageEntity findImageByUuid(String uuid) {
        return imageRepository.findByUuid(uuid).orElseThrow(
                () -> new ImageNotFoundException()
        );
    }

    public ImageEntity findImageByArtworkAndPosition(long artworkId, int position) {
        return imageRepository.findByArtworkIdAfterAndPosition(artworkId, position).orElseThrow(
                () -> new ImageNotFoundException()
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
}