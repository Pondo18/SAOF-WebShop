package de.hdbw.webshop.repository.artwork;

import de.hdbw.webshop.model.artwork.ArtworkEntity;
import org.checkerframework.checker.nullness.Opt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArtworkRepository extends JpaRepository<ArtworkEntity, Long> {
    Optional<ArtworkEntity> findByGeneratedArtworkName (String generatedArtworkName);

    @Query("SELECT id FROM ArtworkEntity WHERE generatedArtworkName = ?1")
    Optional<Long> findArtworkIdByGeneratedArtworkName(String generatedArtworkName);

    Optional<ArtworkEntity> findById(long id);

    List<ArtworkEntity> findAllByAvailableGreaterThan(int available);
}
