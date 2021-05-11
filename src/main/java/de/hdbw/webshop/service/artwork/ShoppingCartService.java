package de.hdbw.webshop.service.artwork;

import de.hdbw.webshop.model.artwork.ShoppingCartEntity;
import de.hdbw.webshop.model.users.AllUsersEntity;
import de.hdbw.webshop.repository.artwork.ShoppingCartRepository;
import de.hdbw.webshop.repository.user.AllUsersRepository;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final AllUsersRepository allUsersRepository;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, AllUsersRepository allUsersRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.allUsersRepository = allUsersRepository;
    }

    public ShoppingCartEntity addArtworkToShoppingCart(long artworkId, long userId) {
        AllUsersEntity allUsersEntity = allUsersRepository.findById(userId);
        shoppingCartRepository.
    }
}
