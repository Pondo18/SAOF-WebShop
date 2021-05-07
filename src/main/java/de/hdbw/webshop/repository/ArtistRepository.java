package de.hdbw.webshop.repository;

import de.hdbw.webshop.model.user.ArtistEntity;
import de.hdbw.webshop.model.user.RegisteredUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArtistRepository extends JpaRepository<ArtistEntity, Long> {
    Optional<ArtistEntity> findByRegisteredUserEntity(RegisteredUserEntity registeredUserEntity);
}
