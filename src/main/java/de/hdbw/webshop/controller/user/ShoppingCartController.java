package de.hdbw.webshop.controller.user;

import de.hdbw.webshop.dto.artwork.ArtworkForListViewDTO;
import de.hdbw.webshop.dto.artwork.ShoppingCartDTO;
import de.hdbw.webshop.exception.exceptions.ArtworkNotFoundException;
import de.hdbw.webshop.exception.exceptions.UserNotFoundException;
import de.hdbw.webshop.service.user.ShoppingCartService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@CommonsLog
@Controller
@RequestMapping("/shopping_cart")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;
    private final MessageSource messageSource;

    public ShoppingCartController(ShoppingCartService shoppingCartService, MessageSource messageSource) {
        this.shoppingCartService = shoppingCartService;
        this.messageSource = messageSource;
    }

    @PostMapping("add_artwork")
    public ModelAndView addArtworkToShoppingCart(HttpServletRequest request,
                                                 Authentication authentication,
                                                 @ModelAttribute("artworkName") String generatedArtworkName,
                                                 RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession();
        try {
            shoppingCartService.addArtworkToShoppingCart(session, authentication, generatedArtworkName);
        } catch (UserNotFoundException | ArtworkNotFoundException e) {
            log.info("There was an error finding the User and Artwork to add to Shoppingcart");
            redirectAttributes.addFlashAttribute("failure", messageSource.getMessage("alert.shopping_cart.add", null, LocaleContextHolder.getLocale()));
        }
        return new ModelAndView("redirect:/artworks/" + generatedArtworkName);
    }

    @PostMapping("remove_artwork")
    public ModelAndView removeArtworkFromShoppingCart(HttpSession session,
                                                      Authentication authentication,
                                                      @ModelAttribute("artworkName") String generatedArtworkName) {
        log.debug("Removing Artwork: " + generatedArtworkName + " from the shoppingCart of user with the session: " + session.getId());
        shoppingCartService.removeArtworkFromShoppingCart(session, authentication, generatedArtworkName);
        return new ModelAndView("redirect:/artworks/" + generatedArtworkName);
    }

    @GetMapping
    public ModelAndView getShoppingCart(HttpServletRequest request, Authentication authentication) {
        HttpSession session = request.getSession();
        List<ArtworkForListViewDTO> artworks = shoppingCartService.getAllArtworksForListViewInShoppingCartBySession(session, authentication);
        ShoppingCartDTO shoppingCartDTO = new ShoppingCartDTO(artworks);
        return new ModelAndView("artworks/shoppingCart", "shoppingCart", shoppingCartDTO);
    }
}
