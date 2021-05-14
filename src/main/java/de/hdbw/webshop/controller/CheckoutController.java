package de.hdbw.webshop.controller;

import de.hdbw.webshop.service.artwork.CheckoutService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {

    final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping
    public ModelAndView doCheckout(HttpSession session, Authentication authentication) {
        checkoutService.doCheckout(session, authentication);
        return new ModelAndView("index");
    }
}
