package de.hdbw.webshop.controller;

import de.hdbw.webshop.dto.ArtworkForDetailedViewDTO;
import de.hdbw.webshop.dto.ArtworkForListViewDTO;
import de.hdbw.webshop.exception.exceptions.ArtworkNotFoundException;
import de.hdbw.webshop.service.artwork.ArtworkDTOService;
import de.hdbw.webshop.service.artwork.ShoppingCartService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@CommonsLog
@Controller
@RequestMapping("/artworks")
public class ArtworkController {

    private final ArtworkDTOService artworkDTOService;
    private final ShoppingCartService shoppingCartService;

    @Autowired
    public ArtworkController(ArtworkDTOService artworkDTOService, ShoppingCartService shoppingCartService) {
        this.artworkDTOService = artworkDTOService;
        this.shoppingCartService = shoppingCartService;
    }


    @GetMapping
    public ModelAndView getAllArtworks() {
        List<ArtworkForListViewDTO> artworks = artworkDTOService.getAllArtworksForArtworksPage();
        log.debug("Returning artworks page");
        return new ModelAndView("artworks/artworks", "artworks", artworks);
    }

    @GetMapping("/{generatedArtworkName}")
    public ModelAndView getArtworkPage(@PathVariable String generatedArtworkName,
                                       HttpServletRequest request,
                                       Authentication authentication) {
        try {
            HttpSession session = request.getSession();
            boolean isInShoppingCart = shoppingCartService.ArtworkIsInShoppingCart(session, authentication, generatedArtworkName);
            ArtworkForDetailedViewDTO artwork = artworkDTOService.getArtworkForDetailedInformationPage(generatedArtworkName);
            log.debug("Returning detailed artwork page for Artwork with generatedArtworkName: " + generatedArtworkName);
            ModelAndView mav = new ModelAndView("artworks/artworkInformation", "artwork", artwork);
            mav.addObject("isInShoppingCart", isInShoppingCart);
            return mav;
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
