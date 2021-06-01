package de.hdbw.webshop.repository.artwork;

import de.hdbw.webshop.model.artwork.entity.BoughtArtworkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckoutRepository extends JpaRepository<BoughtArtworkEntity, Long> {
}
