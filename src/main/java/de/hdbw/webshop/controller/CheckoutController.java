package de.hdbw.webshop.controller;

import de.hdbw.webshop.exception.exceptions.UserNotFoundException;
import de.hdbw.webshop.service.artwork.CheckoutService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/checkout")
public class CheckoutController {

    final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @Secured({ "ROLE_REGISTERED_USER", "ROLE_ARTIST" })
    @PostMapping
    public ModelAndView doCheckout(Authentication authentication) {
        try {
            checkoutService.doCheckout(authentication);
            return new ModelAndView("redirect:/artworks");
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Couldn't find Registered User for Checkout, but he was authorized",
                    e
            );
        }
    }
}
