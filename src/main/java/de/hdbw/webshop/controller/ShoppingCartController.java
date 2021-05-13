package de.hdbw.webshop.controller;

import de.hdbw.webshop.dto.ArtworkForListViewDTO;
import de.hdbw.webshop.service.artwork.ShoppingCartService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/shopping_cart")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @PostMapping("add_artwork")
    public ModelAndView addArtworkToShoppingCart(HttpServletRequest request,
                                                 Authentication authentication,
                                                 @ModelAttribute("artworkName") String generatedArtworkName) {
        HttpSession session = request.getSession();
        shoppingCartService.addArtworkToShoppingCart(session, authentication, generatedArtworkName);
        return new ModelAndView("index");
    }

    @GetMapping
    public ModelAndView getShoppingCart(HttpServletRequest request, Authentication authentication) {
        HttpSession session = request.getSession();
        List<ArtworkForListViewDTO> artworks = shoppingCartService.getAllArtworksInShoppingCartForUserId(session, authentication);
        return new ModelAndView("artworks/shoppingCart", "artworks", artworks);
    }
}
