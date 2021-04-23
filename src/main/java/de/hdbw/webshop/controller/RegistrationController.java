package de.hdbw.webshop.controller;

import de.hdbw.webshop.dto.UserRegistrationFormDTO;
import de.hdbw.webshop.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public String getUserRegistrationForm(Model model) {
        UserRegistrationFormDTO userRegistrationForm = new UserRegistrationFormDTO();
        model.addAttribute("userRegistrationForm", userRegistrationForm);
        return "registration_user";
    }

    @PostMapping("/user")
    public RedirectView registerNewUser(@ModelAttribute UserRegistrationFormDTO userRegistrationForm, BindingResult error, Model model) {
       userService.registerNewUser(userRegistrationForm);
       return new RedirectView("/");
    }
}
