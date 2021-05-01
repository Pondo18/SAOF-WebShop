package de.hdbw.webshop.service;

import de.hdbw.webshop.model.artwork.ImageEntity;
import de.hdbw.webshop.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
        return this.imageRepository.findByUuid(uuid);
    }

    public ImageEntity findImageByArtworkAndPosition(long artworkId, int position) {
        return imageRepository.findByArtworkIdAfterAndPosition(artworkId, position);
    }

//    public List<String> findAllImageNamesByArtworkAndOrderByPosition(long artworkId) {
//        return imageRepository.findAllImageFileNamesByArtworkAndOrderByPosition(artworkId);
//    }

    public ImageEntity getImageByUuid(String uuid) throws Exception {
        ImageEntity image = findByUuid(uuid);
        if (image == null) {
            return ImageEntity.defaultImage();
        }
        return image;
    }
}