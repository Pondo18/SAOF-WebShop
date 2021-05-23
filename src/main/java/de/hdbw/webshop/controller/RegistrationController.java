package de.hdbw.webshop.controller;

import de.hdbw.webshop.dto.registration.ArtistRegistrationFormDTO;
import de.hdbw.webshop.dto.registration.UserRegistrationFormDTO;
import de.hdbw.webshop.model.users.entity.AllUsersEntity;
import de.hdbw.webshop.model.users.entity.ArtistEntity;
import de.hdbw.webshop.service.artwork.ShoppingCartService;
import de.hdbw.webshop.service.authentication.ArtistRegistrationService;
import de.hdbw.webshop.service.authentication.UserRegistrationService;
import de.hdbw.webshop.service.session.RedirectHelper;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@CommonsLog
@Controller
@RequestMapping("/registration")
public class RegistrationController {

    final UserRegistrationService userRegistrationService;
    final ShoppingCartService shoppingCartService;
    final RedirectHelper redirectHelper;
    final ArtistRegistrationService artistRegistrationService;

    public RegistrationController(UserRegistrationService userRegistrationService, ShoppingCartService shoppingCartService, RedirectHelper redirectHelper, ArtistRegistrationService artistRegistrationService) {
        this.userRegistrationService = userRegistrationService;
        this.shoppingCartService = shoppingCartService;
        this.redirectHelper = redirectHelper;
        this.artistRegistrationService = artistRegistrationService;
    }

    @GetMapping("/user")
    public ModelAndView getUserRegistrationForm(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        UserRegistrationFormDTO userRegistrationForm = new UserRegistrationFormDTO();
        ModelAndView modelAndView = new ModelAndView("user/registrationUser", "user", userRegistrationForm);
        if (session != null) {
            modelAndView.addObject("jsessionid", request.getSession().getId());
        }
        String returnUrl = request.getParameter("returnUrl");
        if (returnUrl!=null) {
            modelAndView.addObject("returnUrl", returnUrl);
        }
        return modelAndView;
    }

    @PostMapping("/user")
    public ModelAndView registerNewUser(@Valid UserRegistrationFormDTO userRegistrationForm,
                                        BindingResult bindingResult,
                                        HttpServletRequest httpServletRequest,
                                        HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            log.info("Errors in user registration for email: '" + userRegistrationForm.getEmail()
                    + "' ERRORS: " + bindingResult.getAllErrors() + "'");
            return new ModelAndView("user/registrationUser", "user", userRegistrationForm);
        } else {
            String jsessionid = httpServletRequest.getParameter("jsessionid");
            AllUsersEntity allUsersEntity = userRegistrationService.doRegistration(userRegistrationForm,
                    httpServletRequest, response);
            if (jsessionid!=null) {
                shoppingCartService.changeUserForShoppingCartAndSave(jsessionid, allUsersEntity);
            }
            String refererUrl = httpServletRequest.getParameter("returnUrl");
            if (refererUrl!=null) {
                String returnPath = redirectHelper.getRedirectPath(refererUrl);
                return new ModelAndView("redirect:/"+returnPath);
            }
        }
        log.info("Registering new user with email: '" + userRegistrationForm.getEmail() + "'");
        return new ModelAndView("redirect:/artworks");
    }

    @GetMapping("/artist")
    public ModelAndView getArtistRegistrationForm() {
        ArtistRegistrationFormDTO artistRegistrationFormDTO = new ArtistRegistrationFormDTO();
        return new ModelAndView("artist/registrationArtist", "artist", artistRegistrationFormDTO);
    }

    @PostMapping("/artist")
    public ModelAndView registerNewArtist(@Valid ArtistRegistrationFormDTO artist,
                                          Authentication authentication,
                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("Errors in artist registration registered user with the email: " + authentication.getName()
            + "'ERRORS: " + bindingResult.getAllErrors() + "'");
            return new ModelAndView("artist/registrationArtist", "artist", artist);
        } else {
            artistRegistrationService.registerNewArtist(authentication, artist);
            log.info("Registering new Artist with the email: " + authentication.getName());
            return new ModelAndView("redirect:/artworks");
        }
    }
}

