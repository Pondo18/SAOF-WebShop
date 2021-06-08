package de.hdbw.webshop.service.authentication;

import de.hdbw.webshop.dto.registration.ArtistRegistrationFormDTO;
import de.hdbw.webshop.model.users.entity.ArtistEntity;
import de.hdbw.webshop.model.users.entity.RegisteredUsersEntity;
import de.hdbw.webshop.repository.user.ArtistRepository;
import de.hdbw.webshop.service.artist.ArtistService;
import de.hdbw.webshop.service.user.RegisteredUserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class ArtistRegistrationService {

    private final RegisteredUserService registeredUserService;
    private final ArtistService artistService;
    private final ArtistRepository artistRepository;
    private final AuthenticationService authenticationService;

    public ArtistRegistrationService(RegisteredUserService registeredUserService, ArtistService artistService, ArtistRepository artistRepository, AuthenticationService authenticationService) {
        this.registeredUserService = registeredUserService;
        this.artistService = artistService;
        this.artistRepository = artistRepository;
        this.authenticationService = authenticationService;
    }

    public ArtistEntity registerNewArtist(Authentication authentication, ArtistRegistrationFormDTO artistDTO) {
        RegisteredUsersEntity currentRegisteredUser = registeredUserService.findRegisteredUserEntityByAuthentication(authentication);
        ArtistEntity artistEntity = artistService.createNewArtist(artistDTO, currentRegisteredUser);
        authenticationService.authenticateArtistAfterRegistration(authentication.getPrincipal());
        return artistRepository.save(artistEntity);
    }
}
