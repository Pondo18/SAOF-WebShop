package de.hdbw.webshop.controller.artwork;

import de.hdbw.webshop.dto.artwork.EditMyArtworkDTO;
import de.hdbw.webshop.service.artist.ArtistService;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/create_artwork")
public class AddNewArtworkController {

    final ArtistService artistService;
    final MessageSource messageSource;

    public AddNewArtworkController(ArtistService artistService, MessageSource messageSource) {
        this.artistService = artistService;
        this.messageSource = messageSource;
    }

    @PostMapping
    public ModelAndView addNewArtwork(EditMyArtworkDTO editMyArtworkDTO, Authentication authentication,
                                      RedirectAttributes redirectAttributes) {
        artistService.addNewArtwork(editMyArtworkDTO, authentication);
        redirectAttributes.addFlashAttribute("success", messageSource.getMessage("alert.artwork.add.success", null, LocaleContextHolder.getLocale()));
        return new ModelAndView("redirect:/my_artworks");
    }
}
