package de.hdbw.webshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/{userId}/shoppingCart")
public class ShoppingCartController {

    @PostMapping("/{artworkId}")
    public void addArtworkToShoppingCart(@PathVariable String artworkId,
                                         @PathVariable String userId) {


    }
}
