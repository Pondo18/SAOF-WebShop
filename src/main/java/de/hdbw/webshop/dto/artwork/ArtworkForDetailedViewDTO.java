package de.hdbw.webshop.dto.artwork;

import de.hdbw.webshop.model.artwork.artworks.entity.ArtworkEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArtworkForDetailedViewDTO {
    private String generatedArtworkName;
    private String artworkName;
    private String artist;
    private double price;
    private String description;
    private List<String> imagesUrl;


    public void build (ArtworkEntity entireArtwork) {
        generatedArtworkName = entireArtwork.getGeneratedArtworkName();
        artworkName = entireArtwork.getArtworkName();
        artist = entireArtwork.getArtist().getRegisteredUserEntity().getFirstName()
                + " "
                + entireArtwork.getArtist().getRegisteredUserEntity().getSecondName();
        price = entireArtwork.getPrice();
        description = entireArtwork.getDescription();
    }

    public static List<String> buildImageUrls(List<String> artworkImageUuids, String host) {
        List<String> artworkImageUrls = artworkImageUuids.stream().map(
                uuid -> host +"/image/big/" + uuid
        ).collect(Collectors.toList());
        return artworkImageUrls;
    }
}
