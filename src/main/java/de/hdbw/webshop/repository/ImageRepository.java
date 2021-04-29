package de.hdbw.webshop.repository;

import java.awt.*;
import java.util.List;

import de.hdbw.webshop.model.artwork.ArtworkEntity;
import de.hdbw.webshop.model.artwork.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Long> {

    ImageEntity findByFileName(String fileName);

    ImageEntity findByUuid(String uuid);

    List<ImageEntity> findAllByArtwork(ArtworkEntity artworkEntity);
}