package de.hdbw.webshop.dto;

import de.hdbw.webshop.model.artwork.ArtworkEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArtworkForArtworksPageDTO {
    private String generatedArtworkName;
    private String artworkName;
    private double price;
    private String primaryImageUrl;

    public void build(ArtworkEntity artworkEntity) {
        generatedArtworkName = artworkEntity.getGeneratedArtworkName();
        artworkName = artworkEntity.getArtworkName();
        price = artworkEntity.getPrice();
        primaryImageUrl = "http://localhost:8080/artwork/" +
                artworkEntity.getGeneratedArtworkName() +
                "/images?position=1";
    }
}
