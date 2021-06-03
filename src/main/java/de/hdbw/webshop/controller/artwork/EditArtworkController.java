package de.hdbw.webshop.controller.artwork;

import de.hdbw.webshop.dto.artwork.EditMyArtworkDTO;
import de.hdbw.webshop.service.artwork.artworks.ArtworkService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EditArtworkController {

    final ArtworkService artworkService;

    public EditArtworkController(ArtworkService artworkService) {
        this.artworkService = artworkService;
    }

    @PostMapping("/edit_artwork")
    public ModelAndView editArtwork(EditMyArtworkDTO editMyArtworkDTO,
                                    @ModelAttribute("oldGeneratedArtworkName") String oldGeneratedArtworkName,
                                    Authentication authentication) {
        artworkService.editArtwork(editMyArtworkDTO, authentication, oldGeneratedArtworkName);
        return new ModelAndView("redirect:/my_artworks");
    }
}
