package de.hdbw.webshop.controller;

import de.hdbw.webshop.dto.UserRegistrationFormDTO;
import de.hdbw.webshop.service.UserRegistrationService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@CommonsLog
@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private final UserRegistrationService userRegistrationService;

    public RegistrationController(UserRegistrationService userRegistrationService) {
        this.userRegistrationService = userRegistrationService;
    }

    @GetMapping("/user")
    public ModelAndView getUserRegistrationForm() {
        UserRegistrationFormDTO userRegistrationForm = new UserRegistrationFormDTO();



        return new ModelAndView("user/registrationUser", "user", userRegistrationForm);
    }

    @PostMapping("/user")
    public ModelAndView registerNewUser(@Valid UserRegistrationFormDTO userRegistrationForm,
                                        BindingResult bindingResult, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            log.info("Errors in registration for email: '" + userRegistrationForm.getEmail()
                    + "' ERRORS: '" + bindingResult.getAllErrors() + "'");
            return new ModelAndView("user/registrationUser", "user", userRegistrationForm);
        } else {
            userRegistrationService.doRegistration(userRegistrationForm, httpServletRequest);
            log.info("Registering new user with email: '" + userRegistrationForm.getEmail() + "'");
            return new ModelAndView("index");
        }
    }
}
