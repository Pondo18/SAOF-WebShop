package de.hdbw.webshop.controller.artist;

import de.hdbw.webshop.dto.artwork.ArtworkForListViewDTO;
import de.hdbw.webshop.dto.artwork.EditMyArtworkDTO;
import de.hdbw.webshop.service.artist.ArtistService;
import de.hdbw.webshop.service.artwork.artworks.ArtworkService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class MyArtworksController {

    final ArtistService artistService;
    final ArtworkService artworkService;

    public MyArtworksController(ArtistService artistService, ArtworkService artworkService) {
        this.artistService = artistService;
        this.artworkService = artworkService;
    }

    @Secured({ "ROLE_ARTIST" })
    @GetMapping("/my_artworks")
    public ModelAndView getMyArtworksPage(Authentication authentication) {
        List<ArtworkForListViewDTO> artworksByArtist = artistService.getAllArtworksByArtist(authentication);
        ModelAndView modelAndView = new ModelAndView("artist/myArtworks", "artworkDTO", new EditMyArtworkDTO());
        modelAndView.addObject("artworks", artworksByArtist);
        return modelAndView;
    }
}
