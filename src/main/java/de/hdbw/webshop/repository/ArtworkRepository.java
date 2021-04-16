package de.hdbw.webshop.repository;

import de.hdbw.webshop.model.ArtworkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtworkRepository extends JpaRepository<ArtworkEntity, Long> {
}
