package de.hdbw.webshop.controller.artist;

import de.hdbw.webshop.dto.artwork.CreateNewArtworkDTO;
import de.hdbw.webshop.service.artist.ArtistService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/create_artwork")
public class AddNewArtworkController {

    final ArtistService artistService;

    public AddNewArtworkController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @PostMapping
    public ModelAndView addNewArtwork(CreateNewArtworkDTO createNewArtworkDTO, Authentication authentication) {
        artistService.addNewArtwork(createNewArtworkDTO, authentication);
        return new ModelAndView("redirect:/my_artworks");
    }

    @PostMapping("/test")
    public ModelAndView test(@RequestParam("files") MultipartFile[] files) {
        return new ModelAndView("redirect:/");
    }
}
