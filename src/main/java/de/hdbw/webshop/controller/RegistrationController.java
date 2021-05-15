package de.hdbw.webshop.controller;

import de.hdbw.webshop.dto.UserRegistrationFormDTO;
import de.hdbw.webshop.model.artwork.ShoppingCartEntity;
import de.hdbw.webshop.model.users.entity.AllUsersEntity;
import de.hdbw.webshop.service.artwork.ShoppingCartService;
import de.hdbw.webshop.service.authentication.UserRegistrationService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@CommonsLog
@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private final UserRegistrationService userRegistrationService;
    final ShoppingCartService shoppingCartService;

    public RegistrationController(UserRegistrationService userRegistrationService, ShoppingCartService shoppingCartService) {
        this.userRegistrationService = userRegistrationService;
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping("/user")
    public ModelAndView getUserRegistrationForm() {
        UserRegistrationFormDTO userRegistrationForm = new UserRegistrationFormDTO();
        return new ModelAndView("user/registrationUser", "user", userRegistrationForm);
    }

    @PostMapping("/user")
    public ModelAndView registerNewUser(@Valid UserRegistrationFormDTO userRegistrationForm,
                                        BindingResult bindingResult,
                                        HttpServletRequest httpServletRequest,
                                        @ModelAttribute("returnUrl") String returnUrl,
                                        @ModelAttribute("jsessionid") String jsessionid) {
        if (bindingResult.hasErrors()) {
            log.debug("Errors in registration for email: '" + userRegistrationForm.getEmail()
                    + "' ERRORS: '" + bindingResult.getAllErrors() + "'");
            ModelAndView mav = new ModelAndView("user/registrationUser", "user", userRegistrationForm);
            mav.addObject("returnUrl", returnUrl);
            mav.addObject("jsessionid", jsessionid);
            return mav;
        } else if (returnUrl.equals("/shopping_cart")) {
//            List<ShoppingCartEntity> allShoppingCartEntities = shoppingCartService
//                    .getAllShoppingCartEntitiesForJsessionid(jsessionid);
//            AllUsersEntity allUsersEntity = userRegistrationService.doRegistration(userRegistrationForm, httpServletRequest);
//            shoppingCartService.changeUserForShoppingCartAndSave(allShoppingCartEntities, allUsersEntity);
            return new ModelAndView("redirect:" + returnUrl);
        }
        log.info("Registering new user with email: '" + userRegistrationForm.getEmail() + "'");
        return new ModelAndView("index");
    }
}

