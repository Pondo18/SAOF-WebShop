package de.hdbw.webshop.controller;

import de.hdbw.webshop.model.artwork.ArtworkEntity;
import de.hdbw.webshop.service.ArtworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/artworks")
public class ArtworkController {

    private final ArtworkService artworkService;

    @Autowired
    public ArtworkController(ArtworkService productService) {
        this.artworkService = productService;
    }


    @GetMapping("/all")
    public ModelAndView getAllArtworks() {
        List<ArtworkEntity> artworks = artworkService.getAllArtworks();
        return new ModelAndView("artworks/artworks", "artworks", artworks);
    }

    @GetMapping("/{artworkId}")
    public ModelAndView getArtworkPage(@PathVariable Long artworkId) {
        ArtworkEntity artwork = artworkService.getArtworkById(artworkId);
        return new ModelAndView("artworks/artworkInformation", "artwork", artwork);
    }

}
