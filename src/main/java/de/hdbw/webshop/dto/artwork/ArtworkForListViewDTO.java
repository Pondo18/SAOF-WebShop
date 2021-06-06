package de.hdbw.webshop.dto.artwork;

import de.hdbw.webshop.model.artwork.artworks.entity.ArtworkEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArtworkForListViewDTO {
    private String generatedArtworkName;
    private String artworkName;
    private double price;
    private String primaryImageUrl;

    public ArtworkForListViewDTO build(ArtworkEntity artworkEntity, String host,
                                       String primaryImageUuid, double priceInCorrectCurrency) {
        generatedArtworkName = artworkEntity.getGeneratedArtworkName();
        artworkName = artworkEntity.getArtworkName();
        price = priceInCorrectCurrency;
        primaryImageUrl = host + "/image/small/" + primaryImageUuid;
        return this;
    }
}
