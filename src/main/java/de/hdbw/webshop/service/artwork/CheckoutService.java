package de.hdbw.webshop.service.artwork;

import de.hdbw.webshop.model.artwork.ArtworkEntity;
import de.hdbw.webshop.model.artwork.BoughtArtworkEntity;
import de.hdbw.webshop.model.users.entity.AllUsersEntity;
import de.hdbw.webshop.repository.artwork.CheckoutRepository;
import de.hdbw.webshop.service.user.AllUsersService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CheckoutService {

    final AllUsersService allUsersService;
    final ArtworkService artworkService;
    final ShoppingCartService shoppingCartService;
    final CheckoutRepository checkoutRepository;

    public CheckoutService(AllUsersService allUsersService, ArtworkService artworkService, ShoppingCartService shoppingCartService, CheckoutRepository checkoutRepository) {
        this.allUsersService = allUsersService;
        this.artworkService = artworkService;
        this.shoppingCartService = shoppingCartService;
        this.checkoutRepository = checkoutRepository;
    }

    @Transactional
    public void doCheckout(Authentication authentication) {
        AllUsersEntity currentUser = allUsersService.getCurrentRegisteredUser(authentication);
        List<ArtworkEntity> artworksInShoppingCart = shoppingCartService.convertShoppingCartEntitiesToArtworkEntities(currentUser);
        List<ArtworkEntity> changedArtworks = artworkService.changeAmountOfAvailableArtworks(artworksInShoppingCart);
        artworkService.saveAll(changedArtworks);
        List<BoughtArtworkEntity> boughtArtworkEntities = createBoughtArtworkEntities(changedArtworks, currentUser);
        checkoutRepository.saveAll(boughtArtworkEntities);
        shoppingCartService.removeArtworksFromShoppingCartForUser(changedArtworks, currentUser);

    }

    public List<BoughtArtworkEntity> createBoughtArtworkEntities(List<ArtworkEntity> artworksToBuy, AllUsersEntity currentUser) {
        return artworksToBuy.stream().map(
                artworkEntity -> new BoughtArtworkEntity(artworkEntity, currentUser)
        ).collect(Collectors.toList());
    }
}
