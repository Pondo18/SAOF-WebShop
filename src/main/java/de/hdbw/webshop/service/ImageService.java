package de.hdbw.webshop.service;

import de.hdbw.webshop.model.artwork.ImageEntity;
import de.hdbw.webshop.payload.ImageResponse;
import de.hdbw.webshop.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


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

    public ImageEntity findByFileName(String fileName) {
        return this.imageRepository.findByFileName(fileName);
    }

    public ImageEntity findByUuid(String uuid) {
        return this.imageRepository.findByUuid(uuid);
    }

}