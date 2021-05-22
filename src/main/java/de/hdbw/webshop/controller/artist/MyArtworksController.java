package de.hdbw.webshop.controller.artist;

import de.hdbw.webshop.dto.artwork.ArtworkForListViewDTO;
import de.hdbw.webshop.service.artist.ArtistService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class MyArtworksController {

    final ArtistService artistService;

    public MyArtworksController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping("/my_artworks")
    public ModelAndView getMyArtworksPage(Authentication authentication) {
        List<ArtworkForListViewDTO> artworksByArtist = artistService.getAllArtworksByArtist(authentication);
        return new ModelAndView("artist/myArtworks", "artworks", artworksByArtist);
    }
}
