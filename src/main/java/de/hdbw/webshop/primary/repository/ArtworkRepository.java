package de.hdbw.webshop.primary.repository;

import de.hdbw.webshop.primary.model.ArtworkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtworkRepository extends JpaRepository<ArtworkEntity, Long> {
}
