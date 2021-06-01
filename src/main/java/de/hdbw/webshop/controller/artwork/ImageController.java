package de.hdbw.webshop.controller.artwork;


import de.hdbw.webshop.exception.exceptions.ImageNotFoundException;
import de.hdbw.webshop.model.artwork.entity.ImageEntity;
import de.hdbw.webshop.service.artwork.ImageService;
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


    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }


    @GetMapping("/{uuid}")
    public ResponseEntity<byte[]> getImage(@PathVariable String uuid) {
        try {
            ImageEntity image = imageService.findImageByUuid(uuid);
            log.debug("Return Image by uuid: '" + uuid + "'");
            return ResponseEntity.ok()
                    .contentType(MediaType.valueOf(image.getFileType()))
                    .body(image.getData());
        } catch (ImageNotFoundException imageNotFoundException) {
            log.warn("Could not return Image by Image UUID: '" + uuid + "'");
            try {
                ImageEntity defaultImage = imageService.getLocalImage("static/images/image_missing.png", "image/png");
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
    }
}