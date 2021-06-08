package de.hdbw.webshop.controller.authentication;

import de.hdbw.webshop.dto.registration.ArtistRegistrationFormDTO;
import de.hdbw.webshop.dto.registration.UserRegistrationFormDTO;
import de.hdbw.webshop.model.users.entity.AllUsersEntity;
import de.hdbw.webshop.service.user.ShoppingCartService;
import de.hdbw.webshop.service.authentication.ArtistRegistrationService;
import de.hdbw.webshop.service.authentication.UserRegistrationService;
import de.hdbw.webshop.service.session.RedirectHelper;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@CommonsLog
@Controller
@RequestMapping("/registration")
public class RegistrationController {

    final UserRegistrationService userRegistrationService;
    final ShoppingCartService shoppingCartService;
    final RedirectHelper redirectHelper;
    final ArtistRegistrationService artistRegistrationService;
    final MessageSource messageSource;

    public RegistrationController(UserRegistrationService userRegistrationService, ShoppingCartService shoppingCartService, RedirectHelper redirectHelper, ArtistRegistrationService artistRegistrationService, MessageSource messageSource) {
        this.userRegistrationService = userRegistrationService;
        this.shoppingCartService = shoppingCartService;
        this.redirectHelper = redirectHelper;
        this.artistRegistrationService = artistRegistrationService;
        this.messageSource = messageSource;
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
                                        HttpServletResponse response,
                                        RedirectAttributes redirectAttributes) {
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
            redirectAttributes.addFlashAttribute("success", messageSource.getMessage("alert.registration.success", null, LocaleContextHolder.getLocale()));
            String refererUrl = httpServletRequest.getParameter("returnUrl");
            if (refererUrl!=null) {
                String returnPath = redirectHelper.getRedirectPath(refererUrl);
                return new ModelAndView("redirect:"+returnPath);
            }
            log.info("Registering new user with email: '" + userRegistrationForm.getEmail() + "'");
            return new ModelAndView("redirect:/artworks");
        }
    }

    @GetMapping("/artist")
    public ModelAndView getArtistRegistrationForm() {
        ArtistRegistrationFormDTO artistRegistrationFormDTO = new ArtistRegistrationFormDTO();
        return new ModelAndView("artist/registrationArtist", "artist", artistRegistrationFormDTO);
    }

    @PostMapping("/artist")
    public ModelAndView registerNewArtist(@Valid ArtistRegistrationFormDTO artist,
                                          Authentication authentication,
                                          BindingResult bindingResult,
                                          RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            log.info("Errors in artist registration registered user with the email: " + authentication.getName()
                    + "'ERRORS: " + bindingResult.getAllErrors() + "'");
            return new ModelAndView("artist/registrationArtist", "artist", artist);
        } else {
            artistRegistrationService.registerNewArtist(authentication, artist);
            log.info("Registering new Artist with the email: " + authentication.getName());
            redirectAttributes.addFlashAttribute("success", messageSource.getMessage("alert.registration.success", null, LocaleContextHolder.getLocale()));
            return new ModelAndView("redirect:/artworks");
        }
    }
}
