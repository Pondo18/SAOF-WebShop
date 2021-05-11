package de.hdbw.webshop.repository.artwork;

import de.hdbw.webshop.model.artwork.ShoppingCartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCartEntity, Long> {

}
