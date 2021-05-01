package de.hdbw.webshop.controller;

import de.hdbw.webshop.dto.ArtworkForArtworkInformationPageDTO;
import de.hdbw.webshop.dto.ArtworkForArtworksPageDTO;
import de.hdbw.webshop.service.ArtworkDTOService;
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
    private final ArtworkDTOService artworkDTOService;

    @Autowired
    public ArtworkController(ArtworkService productService, ArtworkDTOService artworkDTOService) {
        this.artworkService = productService;
        this.artworkDTOService = artworkDTOService;
    }


    @GetMapping
    public ModelAndView getAllArtworks() {
        List<ArtworkForArtworksPageDTO> artworks = artworkDTOService.getAllArtworksForArtworksPage();
        return new ModelAndView("artworks/artworks", "artworks", artworks);
    }

    @GetMapping("/{generatedArtworkName}")
    public ModelAndView getArtworkPage(@PathVariable String generatedArtworkName) {
        ArtworkForArtworkInformationPageDTO artwork = artworkDTOService.getArtworkForDetailedInformationPage(generatedArtworkName);
        return new ModelAndView("artworks/artworkInformation", "artwork", artwork);
    }
}
