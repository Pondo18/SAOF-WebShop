package de.hdbw.webshop.controller;

import de.hdbw.webshop.exception.exceptions.ArtworkNotFoundException;
import de.hdbw.webshop.exception.exceptions.ImageNotFoundException;
import de.hdbw.webshop.model.artwork.ImageEntity;
import de.hdbw.webshop.service.ArtworkService;
import de.hdbw.webshop.service.ImageService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@CommonsLog
@RestController
@RequestMapping("/artwork/{artworkName}/images")
public class ArtworkImageController {

    private final ImageService imageService;
    private final ArtworkService artworkService;


    @Autowired
    public ArtworkImageController(ImageService imageService, ArtworkService artworkService) {
        this.imageService = imageService;
        this.artworkService = artworkService;
    }

    @GetMapping
    public ResponseEntity<byte[]> getImageByArtworkAndPosition(@PathVariable String artworkName,
                                                               @RequestParam int position) {
        try {
            long artworkId = artworkService.getArtworkIdByArtworkName(artworkName);
            log.debug("Returning artworkId: '" + artworkId
                    + "' by generatedArtworkName: '" + artworkName + "'");
            ImageEntity imageByArtworkAndPosition = imageService.findImageByArtworkAndPosition(artworkId, position);
            log.debug("Returning primaryImage by generatedArtworkName: '" + artworkName
                    + "' and position: '" + position + "'");
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.valueOf(imageByArtworkAndPosition.getFileType()))
                    .body(imageByArtworkAndPosition.getData()
                    );
        } catch (ArtworkNotFoundException artworkNotFoundException) {
            log.warn("ArtworkId could not be returned for generatedArtworkName: '" + artworkName + "'");
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Artwork Not Found",
                    artworkNotFoundException
            );
        } catch (ImageNotFoundException imageNotFoundException) {
            log.info("Image by position: '" + position
                    + "' and id | for generatedArtworkName: '" + artworkName
                    + "' could not be returned");
            try {
                ImageEntity defaultImage = imageService.getDefaultImage();
                log.info("Returning default image");
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
