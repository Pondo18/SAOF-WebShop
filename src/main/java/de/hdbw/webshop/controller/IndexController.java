package de.hdbw.webshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    @GetMapping
    public ModelAndView redirectToArtworksPage() {
        return new ModelAndView("redirect:/artworks");
    }
}
