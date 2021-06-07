package de.hdbw.webshop.controller.artwork;

import de.hdbw.webshop.dto.artwork.EditMyArtworkDTO;
import de.hdbw.webshop.service.artwork.artworks.ArtworkService;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class EditArtworkController {

    final ArtworkService artworkService;
    final MessageSource messageSource;

    public EditArtworkController(ArtworkService artworkService, MessageSource messageSource) {
        this.artworkService = artworkService;
        this.messageSource = messageSource;
    }

    @PostMapping("/edit_artwork")
    public ModelAndView editArtwork(EditMyArtworkDTO editMyArtworkDTO,
                                    @ModelAttribute("oldGeneratedArtworkName") String oldGeneratedArtworkName,
                                    Authentication authentication, RedirectAttributes redirectAttributes) {
        try {
            artworkService.editArtwork(editMyArtworkDTO, authentication, oldGeneratedArtworkName);
            redirectAttributes.addFlashAttribute("success", messageSource.getMessage("alert.artwork.edit.success", null, LocaleContextHolder.getLocale()));
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("failure", messageSource.getMessage("alert.artwork.edit.failure", null, LocaleContextHolder.getLocale()));
        }
        return new ModelAndView("redirect:/my_artworks");
    }
}
