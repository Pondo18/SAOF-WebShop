package de.hdbw.webshop.service.authentication;

import de.hdbw.webshop.dto.registration.ArtistRegistrationFormDTO;
import de.hdbw.webshop.model.users.entity.ArtistEntity;
import de.hdbw.webshop.model.users.entity.RegisteredUsersEntity;
import de.hdbw.webshop.repository.user.ArtistRepository;
import de.hdbw.webshop.service.user.ArtistService;
import de.hdbw.webshop.service.user.RegisteredUserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class ArtistRegistrationService {

    private final RegisteredUserService registeredUserService;
    private final ArtistService artistService;
    private final ArtistRepository artistRepository;


    public ArtistRegistrationService(RegisteredUserService registeredUserService, ArtistService artistService, ArtistRepository artistRepository) {
        this.registeredUserService = registeredUserService;
        this.artistService = artistService;
        this.artistRepository = artistRepository;
    }

    public ArtistEntity registerNewArtist(Authentication authentication, ArtistRegistrationFormDTO artistDTO) {
        RegisteredUsersEntity currentRegisteredUser = registeredUserService.findRegisteredUserEntityByAuthentication(authentication);
        ArtistEntity artistEntity = artistService.createNewArtist(artistDTO, currentRegisteredUser);
        return artistRepository.save(artistEntity);
    }
}
