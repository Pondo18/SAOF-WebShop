package de.hdbw.webshop.controller.artwork;


import de.hdbw.webshop.exception.exceptions.ImageNotFoundException;
import de.hdbw.webshop.model.artwork.images.Image;
import de.hdbw.webshop.model.artwork.images.entity.DefaultImageEntity;
import de.hdbw.webshop.service.artwork.image.BigImageService;
import de.hdbw.webshop.service.artwork.image.ImageService;
import de.hdbw.webshop.service.artwork.image.MediumImageService;
import de.hdbw.webshop.service.artwork.image.SmallImageService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@CommonsLog
@RestController
@RequestMapping("/image")
public class ImageController {

    private final ImageService imageService;
    private final BigImageService bigImageService;
    private final MediumImageService mediumImageService;
    private final SmallImageService smallImageService;


    @Autowired
    public ImageController(ImageService imageService, BigImageService bigImageService, MediumImageService mediumImageService, SmallImageService smallImageService) {
        this.imageService = imageService;
        this.bigImageService = bigImageService;
        this.mediumImageService = mediumImageService;
        this.smallImageService = smallImageService;
    }


    @GetMapping("/small/{uuid}")
    public ResponseEntity<byte[]> getSmallImage(@PathVariable String uuid) {
        return imageService.getImageResponseEntity(uuid, imageService.getImageByUuid(uuid, smallImageService));
    }

    @GetMapping("/medium/{uuid}")
    public ResponseEntity<byte[]> getMediumImage(@PathVariable String uuid) {
        return imageService.getImageResponseEntity(uuid, imageService.getImageByUuid(uuid, mediumImageService));
    }

    @GetMapping("/big/{uuid}")
    public ResponseEntity<byte[]> getBigImage(@PathVariable String uuid) {
        return imageService.getImageResponseEntity(uuid, imageService.getImageByUuid(uuid, bigImageService));
    }
}