package de.hdbw.webshop.controller;

import de.hdbw.webshop.dto.UserRegistrationFormDTO;
import de.hdbw.webshop.exception.UserAlreadyExistsException;
import de.hdbw.webshop.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private final UserService userService;
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public ModelAndView getUserRegistrationForm() {
        UserRegistrationFormDTO userRegistrationForm = new UserRegistrationFormDTO();
        return new ModelAndView("registrationUser", "user", userRegistrationForm);
    }

    @PostMapping("/user")
    public ModelAndView registerNewUser(@Valid UserRegistrationFormDTO userRegistrationForm,
                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            return new ModelAndView("registrationUser", "user", userRegistrationForm);
        } else {
            try {
                userService.registerNewUser(userRegistrationForm);
                return new ModelAndView("index");
            } catch (UserAlreadyExistsException uaeEx) {
                return new ModelAndView("registrationUser", "user", userRegistrationForm);
            }

        }
//        if (error.hasErrors()) {
//            return "registration_user";
//        } else {
//            model.addAttribute("message", "Success");
//            return "registration_user";
//        }
//        try {
//           userService.registerNewUser(userRegistrationForm);
//        } catch (UserAlreadyExistsException uaeEx) {
//            ModelAndView modelAndView = new ModelAndView("registration_user", "user", userRegistrationForm);
//            String errMessage = "User mit dieser Email existiert bereits";
//            return modelAndView;
//        }
//        return new ModelAndView("index", "user", userRegistrationForm);
//    }
    }
}
