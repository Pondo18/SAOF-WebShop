package de.hdbw.webshop.service.artist;

import de.hdbw.webshop.dto.artwork.ArtworkForListViewDTO;
import de.hdbw.webshop.dto.artwork.EditMyArtworkDTO;
import de.hdbw.webshop.dto.registration.ArtistRegistrationFormDTO;
import de.hdbw.webshop.model.artwork.artworks.entity.ArtworkEntity;
import de.hdbw.webshop.model.users.entity.ArtistEntity;
import de.hdbw.webshop.model.users.entity.RegisteredUsersEntity;
import de.hdbw.webshop.repository.user.ArtistRepository;
import de.hdbw.webshop.service.artwork.artworks.ArtworkService;
import de.hdbw.webshop.service.user.RegisteredUserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ArtistService {

    private final ArtistRepository artistRepository;
    private final RegisteredUserService registeredUserService;
    private final ArtworkService artworkService;

    public ArtistService(ArtistRepository artistRepository, RegisteredUserService registeredUserService, ArtworkService artworkService) {
        this.artistRepository = artistRepository;
        this.registeredUserService = registeredUserService;
        this.artworkService = artworkService;
    }

    public ArtistEntity findArtistIfUserIsArtist(RegisteredUsersEntity registeredUserEntity) {
        return artistRepository.findByRegisteredUserEntity(registeredUserEntity).orElse(null);
    }

    public ArtistEntity createNewArtist(ArtistRegistrationFormDTO artistDTO, RegisteredUsersEntity currentUser) {
        return new ArtistEntity(artistDTO.getCountry(), artistDTO.getDomicile(), currentUser);
    }

    public List<ArtworkForListViewDTO> getAllArtworksByArtist(Authentication authentication) {
        if (authentication!=null) {
            RegisteredUsersEntity currentUser = registeredUserService.findRegisteredUserEntityByAuthentication(authentication);
            return artworkService.findAllArtworksByArtist(currentUser.getArtistEntity());
        } else {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You have to be registered as an Artist to view your artworks"
            );
        }
    }

    public ArtworkEntity addNewArtwork(EditMyArtworkDTO editMyArtworkDTO, Authentication authentication) {
        return artworkService.createNewArtwork(editMyArtworkDTO, authentication);
    }

    public EditMyArtworkDTO getEditMyArtworkDtoIfExisting(Authentication authentication, String generatedArtworkName, List<String> images) {
        if (authentication!=null) {
            RegisteredUsersEntity currentUser = registeredUserService.findRegisteredUserEntityByAuthentication(authentication);
            return artworkService.getEditMyArtworkDTOIfExisting(generatedArtworkName, currentUser);
        } else {
            return null;
        }
    }

}
