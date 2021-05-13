package de.hdbw.webshop.service.artwork;

import de.hdbw.webshop.dto.ArtworkForDetailedViewDTO;
import de.hdbw.webshop.dto.ArtworkForListViewDTO;
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

    public List<ArtworkForListViewDTO> getAllArtworksInShoppingCartForUserId(HttpSession session, Authentication authentication) {
        AllUsersEntity currentUserBySession = allUsersService.getCurrentUserBySession(session, authentication);
        List<ShoppingCartEntity> shoppingCart = shoppingCartRepository.findAllByAllUsersEntity_Id(currentUserBySession.getId());
        List<ArtworkForListViewDTO> artworksForListView = shoppingCart.stream().map(shoppingCartEntity ->
                artworkDTOService.getArtworkForListViewByArtworkEntity(shoppingCartEntity.getArtworkEntity())
        ).collect(Collectors.toList());
        log.debug("Returning all Artworks in ShoppingCart for user with the id: " + currentUserBySession.getId());
        return artworksForListView;
    }


    public boolean ArtworkIsInShoppingCart(HttpSession session, Authentication authentication, String generatedArtworkName) {
        AllUsersEntity currentUserBySession = allUsersService.getCurrentUserBySession(session, authentication);
        return shoppingCartRepository.existsByAllUsersEntity_IdAndArtworkEntity_GeneratedArtworkName(
                currentUserBySession.getId(), generatedArtworkName);
    }
}
