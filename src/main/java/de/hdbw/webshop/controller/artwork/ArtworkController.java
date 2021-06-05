package de.hdbw.webshop.controller.artwork;

import de.hdbw.webshop.dto.artwork.ArtworkForDetailedViewDTO;
import de.hdbw.webshop.dto.artwork.ArtworkForListViewDTO;
import de.hdbw.webshop.dto.artwork.EditMyArtworkDTO;
import de.hdbw.webshop.exception.exceptions.ArtworkNotFoundException;
import de.hdbw.webshop.service.artist.ArtistService;
import de.hdbw.webshop.service.artwork.artworks.ArtworkDTOService;
import de.hdbw.webshop.service.user.ShoppingCartService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Locale;

@CommonsLog
@Controller
@RequestMapping("/artworks")
public class ArtworkController {

    private final ArtworkDTOService artworkDTOService;
    private final ShoppingCartService shoppingCartService;
    private final ArtistService artistService;
    private final MessageSource messageSource;

    @Autowired
    public ArtworkController(ArtworkDTOService artworkDTOService, ShoppingCartService shoppingCartService, ArtistService artistService, MessageSource messageSource) {
        this.artworkDTOService = artworkDTOService;
        this.shoppingCartService = shoppingCartService;
        this.artistService = artistService;
        this.messageSource = messageSource;
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
            EditMyArtworkDTO editArtworkDTO = artistService.getEditMyArtworkDtoIfExisting(authentication, generatedArtworkName, artwork.getImagesUrl());
            log.debug("Returning detailed artwork page for Artwork with generatedArtworkName: " + generatedArtworkName);
            ModelAndView mav = new ModelAndView("artworks/artworkInformation", "artwork", artwork);
            mav.addObject("isInShoppingCart", isInShoppingCart);
            mav.addObject("editArtworkDTO", editArtworkDTO);
            return mav;
        } catch (ArtworkNotFoundException artworkNotFoundException) {
            log.warn("Artwork with the generatedArtworkName: " + generatedArtworkName + " was not found!");
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    messageSource.getMessage("error.artwork.not_found", null, Locale.GERMANY),
                    artworkNotFoundException
            );
        }
    }
}
