package de.hdbw.webshop.controller;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import de.hdbw.webshop.exception.exceptions.ImageNotFoundException;
import de.hdbw.webshop.model.artwork.ImageEntity;
import de.hdbw.webshop.dto.ImageResponseDTO;
import de.hdbw.webshop.service.ImageService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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

    @PostMapping("/upload")
    public ImageResponseDTO uploadSingleFile(@RequestParam("file") MultipartFile file) {
        ImageEntity image = ImageEntity.buildImage(file, 1);
        imageService.save(image);
        return new ImageResponseDTO(image);
    }

    @PostMapping("/uploads")
    public List<ImageResponseDTO> uploadMultiFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files).stream().map(file -> uploadSingleFile(file)).collect(Collectors.toList());
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
                ImageEntity defaultImage = imageService.getDefaultImage();
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