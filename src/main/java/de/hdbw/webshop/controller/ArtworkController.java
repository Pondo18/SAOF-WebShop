package de.hdbw.webshop.controller;

import de.hdbw.webshop.dto.ArtworkForDetailedViewDTO;
import de.hdbw.webshop.dto.ArtworkForListViewDTO;
import de.hdbw.webshop.exception.exceptions.ArtworkNotFoundException;
import de.hdbw.webshop.service.artwork.ArtworkDTOService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@CommonsLog
@Controller
@RequestMapping("/artworks")
public class ArtworkController {

    private final ArtworkDTOService artworkDTOService;

    @Autowired
    public ArtworkController(ArtworkDTOService artworkDTOService) {
        this.artworkDTOService = artworkDTOService;
    }


    @GetMapping
    public ModelAndView getAllArtworks() {
        List<ArtworkForListViewDTO> artworks = artworkDTOService.getAllArtworksForArtworksPage();
        log.debug("Returning artworks page");
        return new ModelAndView("artworks/artworks", "artworks", artworks);
    }

    @GetMapping("/{generatedArtworkName}")
    public ModelAndView getArtworkPage(@PathVariable String generatedArtworkName) {
        try {
            ArtworkForDetailedViewDTO artwork = artworkDTOService.getArtworkForDetailedInformationPage(generatedArtworkName);
            log.debug("Returning detailed artwork page for Artwork with generatedArtworkName: " + generatedArtworkName);
            return new ModelAndView("artworks/artworkInformation", "artwork", artwork);
        } catch (ArtworkNotFoundException artworkNotFoundException) {
            log.warn("Artwork with the generatedArtworkName: " + generatedArtworkName + " was not found!");
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Artwork Not Found",
                    artworkNotFoundException
            );
        }
    }
}
