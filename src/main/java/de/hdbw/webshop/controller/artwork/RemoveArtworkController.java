package de.hdbw.webshop.controller.artwork;

import de.hdbw.webshop.exception.exceptions.ArtworkNotFoundException;
import de.hdbw.webshop.service.artwork.artworks.ArtworkService;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.util.Locale;

@Controller
public class RemoveArtworkController {

    final ArtworkService artworkService;
    final MessageSource messageSource;

    public RemoveArtworkController(ArtworkService artworkService, MessageSource messageSource) {
        this.artworkService = artworkService;
        this.messageSource = messageSource;
    }

    @PostMapping("/remove_artwork")
    public ModelAndView removeArtwork(Authentication authentication,
                                      @ModelAttribute("artworkName") String generatedArtworkName) {
        try {
            artworkService.removeArtworkWithGeneratedArtworkName(generatedArtworkName, authentication);
        } catch (ArtworkNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    messageSource.getMessage("error.artwork.not_yours", null, LocaleContextHolder.getLocale()),
                    e
            );
        }
        return new ModelAndView("redirect:/my_artworks");
    }
}
