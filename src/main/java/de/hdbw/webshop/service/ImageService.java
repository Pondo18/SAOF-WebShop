package de.hdbw.webshop.service;

import de.hdbw.webshop.exception.exceptions.FileNotFoundException;
import de.hdbw.webshop.model.artwork.ImageEntity;
import de.hdbw.webshop.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


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

    public ImageEntity findByUuid(String uuid) {
        return imageRepository.findByUuid(uuid).orElseThrow(() -> new FileNotFoundException());
    }

    public ImageEntity findImageByArtworkAndPosition(long artworkId, int position) {
        return imageRepository.findByArtworkIdAfterAndPosition(artworkId, position);
    }

    public ImageEntity getImageByUuid(String uuid) throws Exception {
        try {
            ImageEntity image = findByUuid(uuid);
            if (image == null) {
                return ImageEntity.defaultImage();
            }
            return image;
        } catch (FileNotFoundException fileNotFoundException) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "File not Found",
                    fileNotFoundException
            );
        }
    }
}