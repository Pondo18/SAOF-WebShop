package de.hdbw.webshop.repository.artwork;

import java.util.List;
import java.util.Optional;

import de.hdbw.webshop.model.artwork.ArtworkEntity;
import de.hdbw.webshop.model.artwork.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Long> {

    Optional<ImageEntity> findByUuid(String uuid);

    @Query("SELECT im from ImageEntity im where im.artwork.id = ?1 and im.position=?2")
    Optional<ImageEntity> findByArtworkIdAfterAndPosition(long artworkId, int position);

    @Query("SELECT uuid FROM ImageEntity WHERE artwork.id = ?1 order by position")
    List<String> findAllImageUuidsByArtworkAndOrderByPosition(long artworkId);

}