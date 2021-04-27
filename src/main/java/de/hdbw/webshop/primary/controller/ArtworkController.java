package de.hdbw.webshop.primary.controller;

import de.hdbw.webshop.primary.model.ArtworkEntity;
import de.hdbw.webshop.primary.service.ArtworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/artworks")
public class ArtworkController {

    private final ArtworkService artworkService;

    @Autowired
    public ArtworkController(ArtworkService productService) {
        this.artworkService = productService;
    }


    @GetMapping
    public List<ArtworkEntity> getAllBooks() {
        return artworkService.getAllArtworks();
    }

}
