package de.hdbw.webshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @GetMapping("/login")
    public ModelAndView getLoginPage(HttpServletRequest request){
        HttpSession httpSession = request.getSession(false);
        ModelAndView modelAndView = new ModelAndView("user/login");
        if (httpSession!=null) {
            request.setAttribute("jsessionid", httpSession.getId());
            modelAndView.addObject("jsessionid", httpSession.getId());
        }
        return modelAndView;
    }
}
