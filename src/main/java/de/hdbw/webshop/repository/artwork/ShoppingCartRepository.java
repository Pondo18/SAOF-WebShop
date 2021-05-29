package de.hdbw.webshop.repository.artwork;

import de.hdbw.webshop.model.artwork.entity.ArtworkEntity;
import de.hdbw.webshop.model.artwork.entity.ShoppingCartEntity;
import de.hdbw.webshop.model.users.entity.AllUsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCartEntity, Long> {
    List<ShoppingCartEntity> findAllByAllUsersEntity_Id(long userId);

    boolean existsByAllUsersEntity_IdAndArtworkEntity_GeneratedArtworkName(long userId, String generatedArtworkName);

    @Transactional
    long deleteByArtworkEntity_GeneratedArtworkNameAndAllUsersEntity_Id(String generatedArtworkName, long userId);

    long deleteByAllUsersEntityAndArtworkEntity(AllUsersEntity allUsersEntity, ArtworkEntity artworkEntity);

    List<ShoppingCartEntity> findAllByAllUsersEntity_UnregisteredUser_Jsessionid(String jsessionid);
}
