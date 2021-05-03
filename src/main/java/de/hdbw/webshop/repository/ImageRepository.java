package de.hdbw.webshop.repository;

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

    List<ImageEntity> findAllByArtwork(ArtworkEntity artworkEntity);

    @Query("SELECT im from ImageEntity im where im.artwork.id = ?1 and im.position=?2")
    ImageEntity findByArtworkIdAfterAndPosition(long artworkId, int position);

//        @Query("SELECT ImageEntity.generatedFileName FROM ImageEntity WHERE ImageEntity.artwork.id = ?1 ORDER BY position")
    @Query("SELECT uuid FROM ImageEntity WHERE artwork.id = ?1 order by position")
    List<String> findAllImageUuidsByArtworkAndOrderByPosition(long artworkId);

}