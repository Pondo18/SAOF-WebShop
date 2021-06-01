package de.hdbw.webshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NavBarController {

    @GetMapping("/contact")
    public ModelAndView getContact() {
        return new ModelAndView("user/contact");
    }

    @GetMapping("/contact_us")
    public ModelAndView getContactUs() {
        return new ModelAndView("user/contactus");
    }

    @GetMapping("/account")
    public ModelAndView getAccount() {
        return new ModelAndView("user/account");
    }
}