package de.hdbw.webshop.controller;

import de.hdbw.webshop.model.artwork.ImageEntity;
import de.hdbw.webshop.service.ArtworkService;
import de.hdbw.webshop.service.ImageService;
import de.hdbw.webshop.util.images.NameHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/artwork/{artworkName}/images")
public class ArtworkImageController {

    private final ImageService imageService;
    private final ArtworkService artworkService;
    private NameHelper fileHelper = new NameHelper();


    @Autowired
    public ArtworkImageController(ImageService imageService, ArtworkService artworkService) {
        this.imageService = imageService;
        this.artworkService = artworkService;
    }

    @GetMapping
    public ResponseEntity<byte[]> getImageByArtworkAndPosition(@PathVariable String artworkName,
                                                               @RequestParam int position) {
        long artworkId = artworkService.getArtworkIdByArtworkName(artworkName);
        ImageEntity imageByArtworkAndPosition = imageService.findImageByArtworkAndPosition(artworkId, position);
        return ResponseEntity.ok().contentType(MediaType.valueOf(imageByArtworkAndPosition.getFileType())).body(imageByArtworkAndPosition.getData());
    }

//    @GetMapping
//    public List<String> getImageFileNamesByArtworkName(@PathVariable String artworkName) {
//        int artworkId = artworkService.getArtworkIdByArtworkName(artworkName);
//        return imageService.findAllImageNamesByArtworkAndOrderByPosition(artworkId);
//    }

}
