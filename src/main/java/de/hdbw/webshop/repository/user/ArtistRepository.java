package de.hdbw.webshop.repository.user;

import de.hdbw.webshop.model.users.ArtistEntity;
import de.hdbw.webshop.model.users.RegisteredUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArtistRepository extends JpaRepository<ArtistEntity, Long> {
    Optional<ArtistEntity> findByRegisteredUserEntity(RegisteredUserEntity registeredUserEntity);
}
