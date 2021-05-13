package de.hdbw.webshop.repository.artwork;

import de.hdbw.webshop.model.artwork.ShoppingCartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCartEntity, Long> {
    List<ShoppingCartEntity> findAllByAllUsersEntity_Id(long userId);
}
