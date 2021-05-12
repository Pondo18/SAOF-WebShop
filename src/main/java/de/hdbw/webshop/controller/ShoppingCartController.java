package de.hdbw.webshop.controller;

import de.hdbw.webshop.service.artwork.ShoppingCartService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
}
