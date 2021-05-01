package de.hdbw.webshop.repository;

import de.hdbw.webshop.model.artwork.ArtworkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArtworkRepository extends JpaRepository<ArtworkEntity, Long> {
    Optional<ArtworkEntity> findByGeneratedArtworkName (String generatedArtworkName);

    @Query("SELECT id FROM ArtworkEntity WHERE generatedArtworkName = ?1")
    long findArtworkIdByGeneratedArtworkName(String generatedArtworkName);
}
