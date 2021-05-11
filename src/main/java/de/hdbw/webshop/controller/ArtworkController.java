package de.hdbw.webshop.controller;

import de.hdbw.webshop.dto.ArtworkForArtworkInformationPageDTO;
import de.hdbw.webshop.dto.ArtworkForArtworksPageDTO;
import de.hdbw.webshop.exception.exceptions.ArtworkNotFoundException;
import de.hdbw.webshop.service.artwork.ArtworkDTOService;
import de.hdbw.webshop.service.artwork.ArtworkService;
import de.hdbw.webshop.service.cookie.SessionService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@CommonsLog
@Controller
@RequestMapping("/artworks")
public class ArtworkController {

    private final ArtworkService artworkService;
    private final ArtworkDTOService artworkDTOService;
    private final SessionService sessionService;

    @Autowired
    public ArtworkController(ArtworkService artworkService, ArtworkDTOService artworkDTOService, SessionService sessionService) {
        this.artworkService = artworkService;
        this.artworkDTOService = artworkDTOService;
        this.sessionService = sessionService;
    }


    @GetMapping
    public ModelAndView getAllArtworks(HttpServletRequest request, HttpServletResponse response) {
        sessionService.createSessionIfNotExisting(request, response);
        List<ArtworkForArtworksPageDTO> artworks = artworkDTOService.getAllArtworksForArtworksPage();
        log.debug("Returning artworks page");
        return new ModelAndView("artworks/artworks", "artworks", artworks);
    }

    @GetMapping("/{generatedArtworkName}")
    public ModelAndView getArtworkPage(@PathVariable String generatedArtworkName) {
        try {
            ArtworkForArtworkInformationPageDTO artwork = artworkDTOService.getArtworkForDetailedInformationPage(generatedArtworkName);
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
