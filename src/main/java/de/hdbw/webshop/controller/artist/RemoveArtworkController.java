package de.hdbw.webshop.controller.artist;

import de.hdbw.webshop.exception.exceptions.ArtworkNotFoundException;
import de.hdbw.webshop.service.artwork.ArtworkService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RemoveArtworkController {

    final ArtworkService artworkService;

    public RemoveArtworkController(ArtworkService artworkService) {
        this.artworkService = artworkService;
    }

    @PostMapping("/remove_artwork")
    public ModelAndView removeArtwork(Authentication authentication,
                                      @ModelAttribute("artworkName") String generatedArtworkName) {
        try {
            artworkService.removeArtworkWithGeneratedArtworkName(generatedArtworkName, authentication);
        } catch (ArtworkNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Couldn't delete Artwork, because it doesn't belong you",
                    e
            );
        }
        return new ModelAndView("redirect:/my_artworks");
    }
}
