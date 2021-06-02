package de.hdbw.webshop.repository.artwork.image;

import java.util.List;
import java.util.Optional;

import de.hdbw.webshop.model.artwork.images.entity.DefaultImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
public interface DefaultImageRepository extends JpaRepository<DefaultImageEntity, Long> {

    @Query("SELECT im from DefaultImageEntity im where im.artwork.id = ?1 and im.position=?2")
    Optional<DefaultImageEntity> findByArtworkIdAfterAndPosition(long artworkId, int position);

//    @Query("SELECT uuid FROM DefaultImageEntity WHERE artwork.id = ?1 order by position")
//    List<String> findAllImageUuidsByArtworkAndOrderByPosition(long artworkId);
}