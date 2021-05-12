package de.hdbw.webshop.service.artwork;

import de.hdbw.webshop.exception.exceptions.ArtworkNotFoundException;
import de.hdbw.webshop.exception.exceptions.UserNotFoundException;
import de.hdbw.webshop.model.artwork.ArtworkEntity;
import de.hdbw.webshop.model.artwork.ShoppingCartEntity;
import de.hdbw.webshop.model.users.entity.AllUsersEntity;
import de.hdbw.webshop.repository.artwork.ShoppingCartRepository;
import de.hdbw.webshop.service.user.AllUsersService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpSession;

@Service
@CommonsLog
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final AllUsersService allUsersService;
    private final ArtworkService artworkService;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, AllUsersService allUsersService, ArtworkService artworkService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.allUsersService = allUsersService;
        this.artworkService = artworkService;
    }

    public ShoppingCartEntity addArtworkToShoppingCart(HttpSession httpSession,
                                                       Authentication authentication,
                                                       String artworkName) {
        try {
            AllUsersEntity currentUserBySession = allUsersService.getCurrentUserBySession(httpSession, authentication);
            ArtworkEntity artworkToAdd = artworkService.getArtworkEntityByGeneratedArtworkName(artworkName);
            return shoppingCartRepository.save(new ShoppingCartEntity(currentUserBySession, artworkToAdd));
        } catch (UserNotFoundException | ArtworkNotFoundException e) {
            log.error("Couldn't get User and Artwork to add to ShoppingCart " + e);
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Couldn't get User and Artwork to add to ShoppingCart",
                    e
            );
        }
    }
}
