package de.hdbw.webshop.service.artist;

import de.hdbw.webshop.dto.artwork.ArtworkForListViewDTO;
import de.hdbw.webshop.dto.registration.ArtistRegistrationFormDTO;
import de.hdbw.webshop.model.users.entity.ArtistEntity;
import de.hdbw.webshop.model.users.entity.RegisteredUsersEntity;
import de.hdbw.webshop.repository.user.ArtistRepository;
import de.hdbw.webshop.service.artwork.ArtworkDTOService;
import de.hdbw.webshop.service.user.RegisteredUserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistService {

    private final ArtistRepository artistRepository;
    private final RegisteredUserService registeredUserService;
    private final ArtworkDTOService artworkDTOService;

    public ArtistService(ArtistRepository artistRepository, RegisteredUserService registeredUserService, ArtworkDTOService artworkDTOService) {
        this.artistRepository = artistRepository;
        this.registeredUserService = registeredUserService;
        this.artworkDTOService = artworkDTOService;
    }

    public ArtistEntity findArtistIfUserIsArtist(RegisteredUsersEntity registeredUserEntity) {
        return artistRepository.findByRegisteredUserEntity(registeredUserEntity).orElse(null);
    }

    public ArtistEntity createNewArtist(ArtistRegistrationFormDTO artistDTO, RegisteredUsersEntity currentUser) {
        return new ArtistEntity(artistDTO.getCountry(), artistDTO.getDomicile(), currentUser);
    }

    public List<ArtworkForListViewDTO> getAllArtworksByArtist(Authentication authentication) {
        RegisteredUsersEntity currentUser = registeredUserService.findRegisteredUserEntityByAuthentication(authentication);
        return artworkDTOService.getAllArtworksByArtist(currentUser);
    }
}
