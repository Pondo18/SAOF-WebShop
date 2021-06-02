package de.hdbw.webshop.repository.artwork.image;

import de.hdbw.webshop.model.artwork.images.entity.BigSizedImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BigImageRepository extends JpaRepository<BigSizedImageEntity, String> {

//    @Query("SELECT uuid FROM BigSizedImageEntity WHERE ArtworkEntity.id = ?1 order by defaultImage.position")
    @Query("SELECT b.uuid FROM BigSizedImageEntity b WHERE b.defaultImage.artwork.id =?1 order by b.defaultImage.position")
    List<String> findAllBigImageUuidsByArtworkAndOrderByPosition(long artworkId);

}
