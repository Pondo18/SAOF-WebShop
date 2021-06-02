package de.hdbw.webshop.repository.artwork.image;

import de.hdbw.webshop.model.artwork.images.entity.MediumSizedImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediumImageRepository extends JpaRepository<MediumSizedImageEntity, String> {
}
