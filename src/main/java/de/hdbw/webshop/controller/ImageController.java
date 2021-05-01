package de.hdbw.webshop.controller;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import de.hdbw.webshop.model.artwork.ImageEntity;
import de.hdbw.webshop.dto.ImageResponseDTO;
import de.hdbw.webshop.service.ArtworkService;
import de.hdbw.webshop.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/image")
public class ImageController {


    private final ImageService imageService;
    private final ArtworkService artworkService;


    @Autowired
    public ImageController(ImageService imageService, ArtworkService artworkService) {
        this.imageService = imageService;
        this.artworkService = artworkService;
    }


    @PostMapping("/upload")
    public ImageResponseDTO uploadSingleFile(@RequestParam("file") MultipartFile file) {
//        ArtworkEntity artworkEntity = artworkService.getArtworkById(1);
        ImageEntity image = ImageEntity.buildImage(file, 1);
        imageService.save(image);
        return new ImageResponseDTO(image);
    }

    @PostMapping("/uploads")
    public List<ImageResponseDTO> uploadMultiFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files).stream().map(file -> uploadSingleFile(file)).collect(Collectors.toList());
    }


    @GetMapping("/{uuid}")
    public ResponseEntity<byte[]> getImage(@PathVariable String uuid) throws Exception {
        ImageEntity image = imageService.getImageByUuid(uuid);
        return ResponseEntity.ok().contentType(MediaType.valueOf(image.getFileType())).body(image.getData());
    }
}