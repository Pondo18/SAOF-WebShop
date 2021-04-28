package de.hdbw.webshop.controller;

import de.hdbw.webshop.model.ArtworkEntity;
import de.hdbw.webshop.service.ArtworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/artworks")
public class ArtworksController {

    private final ArtworkService artworkService;

    @Autowired
    public ArtworksController(ArtworkService productService) {
        this.artworkService = productService;
    }


    @GetMapping("/all")
    public ModelAndView getAllArtworks() {
        List<ArtworkEntity> artworks = artworkService.getAllArtworks();
        return new ModelAndView("artworks/artworks", "artworks", artworks);
    }

}
