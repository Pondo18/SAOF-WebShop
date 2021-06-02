package de.hdbw.webshop.service.user;

import de.hdbw.webshop.dto.artwork.ArtworkForListViewDTO;
import de.hdbw.webshop.exception.exceptions.ArtworkNotFoundException;
import de.hdbw.webshop.exception.exceptions.UserNotFoundException;
import de.hdbw.webshop.model.artwork.artworks.entity.ArtworkEntity;
import de.hdbw.webshop.model.artwork.artworks.entity.ShoppingCartEntity;
import de.hdbw.webshop.model.users.entity.AllUsersEntity;
import de.hdbw.webshop.repository.artwork.ShoppingCartRepository;
import de.hdbw.webshop.service.artwork.artworks.ArtworkDTOService;
import de.hdbw.webshop.service.artwork.artworks.ArtworkService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Service
@CommonsLog
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final AllUsersService allUsersService;
    private final ArtworkService artworkService;
    private final ArtworkDTOService artworkDTOService;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, AllUsersService allUsersService, ArtworkService artworkService, ArtworkDTOService artworkDTOService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.allUsersService = allUsersService;
        this.artworkService = artworkService;
        this.artworkDTOService = artworkDTOService;
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

    public long removeArtworkFromShoppingCart(HttpSession session,
                                              Authentication authentication,
                                              String artworkName) {
        AllUsersEntity currentUserBySession = allUsersService.getCurrentUserBySession(session, authentication);
        return shoppingCartRepository.deleteByArtworkEntity_GeneratedArtworkNameAndAllUsersEntity_Id(
                artworkName, currentUserBySession.getId());
    }

    public List<ArtworkForListViewDTO> getAllArtworksForListViewInShoppingCartBySession(HttpSession session, Authentication authentication) {
        AllUsersEntity currentUserBySession = allUsersService.getCurrentUserBySession(session, authentication);
        List<ShoppingCartEntity> shoppingCart = getAllShoppingCartEntitiesForUser(currentUserBySession);
        List<ArtworkForListViewDTO> artworksForListView = shoppingCart.stream().map(shoppingCartEntity ->
                artworkDTOService.getArtworkForListViewByArtworkEntity(shoppingCartEntity.getArtworkEntity())
        ).collect(Collectors.toList());
        log.debug("Returning all Artworks in ShoppingCart for user with the id: " + currentUserBySession.getId());
        return artworksForListView;
    }

    public List<ShoppingCartEntity> getAllShoppingCartEntitiesForUser(AllUsersEntity currentUser) {
        return shoppingCartRepository.findAllByAllUsersEntity_Id(currentUser.getId());
    }

    public List<ShoppingCartEntity> getAllShoppingCartEntitiesForJsessionid(String jsessionid) {
        return shoppingCartRepository.findAllByAllUsersEntity_UnregisteredUser_Jsessionid(jsessionid);
    }

    public List<ArtworkEntity> convertShoppingCartEntitiesToArtworkEntities(AllUsersEntity currentUser) {
        List<ShoppingCartEntity> shoppingCart = getAllShoppingCartEntitiesForUser(currentUser);
        return shoppingCart.stream().map(
                ShoppingCartEntity::getArtworkEntity
        ).collect(Collectors.toList());
    }

    public List<Long> removeArtworksFromShoppingCartForUser(List<ArtworkEntity> artworksToRemove, AllUsersEntity currentUser) {
        return artworksToRemove.stream().map(
                artworkEntity -> shoppingCartRepository.deleteByAllUsersEntityAndArtworkEntity(currentUser, artworkEntity)
        ).collect(Collectors.toList());
    }


    public boolean ArtworkIsInShoppingCart(HttpSession session, Authentication authentication, String generatedArtworkName) {
        AllUsersEntity currentUserBySession = allUsersService.getCurrentUserBySession(session, authentication);
        return shoppingCartRepository.existsByAllUsersEntity_IdAndArtworkEntity_GeneratedArtworkName(
                currentUserBySession.getId(), generatedArtworkName);
    }

    public boolean artworkIsNotInShoppingCartYet(ShoppingCartEntity shoppingCartEntity) {
        return !shoppingCartRepository.existsByAllUsersEntity_IdAndArtworkEntity_GeneratedArtworkName(
                shoppingCartEntity.getAllUsersEntity().getId(),
                shoppingCartEntity.getArtworkEntity().getGeneratedArtworkName());
    }

    public List<ShoppingCartEntity> changeUserForShoppingCartAndSave(String jsessionidFromUnregisteredSession,
                                                                     Authentication authenticationFromRegisteredSession) {
        AllUsersEntity newRegisteredUser = allUsersService.getCurrentRegisteredUser(authenticationFromRegisteredSession);
        return changeUserForShoppingCartAndSave(jsessionidFromUnregisteredSession, newRegisteredUser);
    }

    public List<ShoppingCartEntity> changeUserForShoppingCartAndSave(String jsessionidFromUnregisteredSession,
                                                                     AllUsersEntity newRegisteredUser) {
        AllUsersEntity oldUnregisteredUser = allUsersService.getCurrentUnregisteredUser(jsessionidFromUnregisteredSession);
        List<ShoppingCartEntity> oldShoppingCart = getAllShoppingCartEntitiesForUser(oldUnregisteredUser);
        oldShoppingCart.forEach(shoppingCartEntity -> shoppingCartEntity.setAllUsersEntity(newRegisteredUser));
        List<ShoppingCartEntity> newShoppingCart =  oldShoppingCart.stream()
                .filter(this::artworkIsNotInShoppingCartYet)
                .collect(Collectors.toList());
        return shoppingCartRepository.saveAll(newShoppingCart);
    }
}
