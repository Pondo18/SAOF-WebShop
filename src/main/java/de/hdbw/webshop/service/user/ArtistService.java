package de.hdbw.webshop.service.user;

import de.hdbw.webshop.model.users.entity.ArtistEntity;
import de.hdbw.webshop.model.users.entity.RegisteredUsersEntity;
import de.hdbw.webshop.repository.user.ArtistRepository;
import org.springframework.stereotype.Service;

@Service
public class ArtistService {

    private final ArtistRepository artistRepository;

    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public ArtistEntity findArtistIfUserIsArtist(RegisteredUsersEntity registeredUserEntity) {
        return artistRepository.findByRegisteredUserEntity(registeredUserEntity).orElse(null);
    }
}
