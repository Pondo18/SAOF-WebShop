package de.hdbw.webshop.repository.artwork;

import de.hdbw.webshop.model.artwork.artworks.entity.ArtworkEntity;
import de.hdbw.webshop.model.users.entity.ArtistEntity;
import de.hdbw.webshop.model.users.entity.RegisteredUsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface ArtworkRepository extends JpaRepository<ArtworkEntity, Long> {
    Optional<ArtworkEntity> findByGeneratedArtworkName (String generatedArtworkName);

    List<ArtworkEntity> findAllByAvailableGreaterThan(int available);

    List<ArtworkEntity> findAllByArtistAndAvailable(ArtistEntity artistEntity, int available);

    boolean existsByGeneratedArtworkName(String generatedArtworkName);

    boolean existsByGeneratedArtworkNameAndArtist_RegisteredUserEntity(String generatedArtworkName, RegisteredUsersEntity registeredUsersEntity);

    Optional<ArtworkEntity> findByGeneratedArtworkNameAndArtist_RegisteredUserEntity(String generatedArtworkName, RegisteredUsersEntity registeredUsersEntity);

    @Transactional
    long deleteByGeneratedArtworkName(String generatedArtworkName);
}
