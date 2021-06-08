package de.hdbw.webshop.controller.user;

import de.hdbw.webshop.exception.exceptions.UserNotFoundException;
import de.hdbw.webshop.service.user.CheckoutService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@CommonsLog
@Controller
@RequestMapping("/checkout")
public class CheckoutController {

    final CheckoutService checkoutService;
    final MessageSource messageSource;

    public CheckoutController(CheckoutService checkoutService, MessageSource messageSource) {
        this.checkoutService = checkoutService;
        this.messageSource = messageSource;
    }

    @Secured({ "ROLE_REGISTERED_USER", "ROLE_ARTIST" })
    @PostMapping
    public ModelAndView doCheckout(Authentication authentication, RedirectAttributes redirectAttributes) {
        try {
            checkoutService.doCheckout(authentication);
            redirectAttributes.addFlashAttribute("success", messageSource.getMessage("alert.user.checkout.success", null, LocaleContextHolder.getLocale()));
        } catch (UserNotFoundException e) {
            log.info("Couldn't find Registered User for Checkout, but he was authorized");
            redirectAttributes.addFlashAttribute("failure", messageSource.getMessage("alert.user.checkout.failure", null, LocaleContextHolder.getLocale()));
        }
        return new ModelAndView("redirect:/artworks");
    }
}
