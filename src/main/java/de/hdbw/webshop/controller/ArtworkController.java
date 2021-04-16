package de.hdbw.webshop.controller;

import de.hdbw.webshop.model.ArtworkEntity;
import de.hdbw.webshop.service.ArtworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/artworks")
public class ArtworkController {

    private final ArtworkService productService;

    @Autowired
    public ArtworkController(ArtworkService productService) {
        this.productService = productService;
    }


    @GetMapping
    public List<ArtworkEntity> getAllBooks() {
        return productService.getAllArtworks();
    }

}
