package de.hdbw.webshop.repository.user;

import de.hdbw.webshop.model.users.entity.ArtistEntity;
import de.hdbw.webshop.model.users.entity.RegisteredUsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArtistRepository extends JpaRepository<ArtistEntity, Long> {
    Optional<ArtistEntity> findByRegisteredUserEntity(RegisteredUsersEntity registeredUserEntity);
}
