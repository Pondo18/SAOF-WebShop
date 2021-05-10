package de.hdbw.webshop.service.user;

import de.hdbw.webshop.model.users.ArtistEntity;
import de.hdbw.webshop.model.users.RegisteredUserEntity;
import de.hdbw.webshop.repository.ArtistRepository;
import org.springframework.stereotype.Service;

@Service
public class ArtistService {

    private final ArtistRepository artistRepository;

    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public ArtistEntity findArtistIfUserIsArtist(RegisteredUserEntity registeredUserEntity) {
        return artistRepository.findByRegisteredUserEntity(registeredUserEntity).orElse(null);
    }
}
