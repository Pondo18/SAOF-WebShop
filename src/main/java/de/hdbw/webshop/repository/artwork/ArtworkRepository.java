package de.hdbw.webshop.repository.artwork;

import de.hdbw.webshop.model.artwork.ArtworkEntity;
import de.hdbw.webshop.model.users.entity.ArtistEntity;
import de.hdbw.webshop.model.users.entity.RegisteredUsersEntity;
import org.checkerframework.checker.nullness.Opt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface ArtworkRepository extends JpaRepository<ArtworkEntity, Long> {
    Optional<ArtworkEntity> findByGeneratedArtworkName (String generatedArtworkName);

    @Query("SELECT id FROM ArtworkEntity WHERE generatedArtworkName = ?1")
    Optional<Long> findArtworkIdByGeneratedArtworkName(String generatedArtworkName);

    Optional<ArtworkEntity> findById(long id);

    List<ArtworkEntity> findAllByAvailableGreaterThan(int available);

    List<ArtworkEntity> findAllByArtistAndAvailable(ArtistEntity artistEntity, int available);

    boolean existsByGeneratedArtworkName(String generatedArtworkName);

    boolean existsByGeneratedArtworkNameAndArtist_RegisteredUserEntity(String generatedArtworkName, RegisteredUsersEntity registeredUsersEntity);

    @Transactional
    long deleteByGeneratedArtworkName(String generatedArtworkName);
}
