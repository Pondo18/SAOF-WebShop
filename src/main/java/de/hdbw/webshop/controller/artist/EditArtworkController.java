package de.hdbw.webshop.controller.artist;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EditArtworkController {

    @PostMapping("/edit_artwork")
    public ModelAndView editArtwork() {
        return null;
    }
}
