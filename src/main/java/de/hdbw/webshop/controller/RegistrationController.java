package de.hdbw.webshop.controller;

import de.hdbw.webshop.dto.UserRegistrationFormDTO;
import de.hdbw.webshop.model.users.entity.AllUsersEntity;
import de.hdbw.webshop.service.artwork.ShoppingCartService;
import de.hdbw.webshop.service.authentication.UserRegistrationService;
import de.hdbw.webshop.service.session.RedirectService;
import lombok.extern.apachecommons.CommonsLog;
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
    final RedirectService redirectService;

    public RegistrationController(UserRegistrationService userRegistrationService, ShoppingCartService shoppingCartService, RedirectService redirectService) {
        this.userRegistrationService = userRegistrationService;
        this.shoppingCartService = shoppingCartService;
        this.redirectService = redirectService;
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
            log.info("Errors in registration for email: '" + userRegistrationForm.getEmail()
                    + "' ERRORS: '" + bindingResult.getAllErrors() + "'");
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
                String returnPath = redirectService.getRedirectPath(refererUrl);
                return new ModelAndView("redirect:/"+returnPath);
            }
        }
        log.info("Registering new user with email: '" + userRegistrationForm.getEmail() + "'");
        return new ModelAndView("index");
    }

    // PROBLEME:
    //Session wird nicht zerstört und geladen
}

