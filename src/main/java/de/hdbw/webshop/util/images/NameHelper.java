package de.hdbw.webshop.util.images;

import de.hdbw.webshop.model.artwork.ArtworkEntity;


public class NameHelper {

    public String generateArtworkDisplayName(ArtworkEntity artworkEntity) {
        String artworkName = artworkEntity.getArtworkName();
        long artworkId = artworkEntity.getId();
        artworkName = artworkName.replace(" ", "_").toLowerCase();
        artworkName = artworkName + "_" + artworkId;
        return artworkName;
    }
}