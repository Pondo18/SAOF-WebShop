package de.hdbw.webshop.dto;

import de.hdbw.webshop.model.artwork.ArtworkEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArtworkForArtworkInformationPageDTO {
    private String generatedArtworkName;
    private String artworkName;
    private String artist;
    private double price;
    private String description;
    private List<String> imagesUrl;


    public void build (ArtworkEntity entireArtwork) {
        generatedArtworkName = entireArtwork.getGeneratedArtworkName();
        artworkName = entireArtwork.getArtworkName();
        artist = entireArtwork.getArtist();
        price = entireArtwork.getPrice();
        description = entireArtwork.getDescription();
    }

    public static List<String> buildImageUrls(List<String> artworkImageUuids) {
        List<String> artworkImageUrls = new ArrayList<>();
        for (String imageUuid : artworkImageUuids) {
            String imageUrl = "http://localhost:8080/image/" + imageUuid;
            artworkImageUrls.add(imageUrl);
        }
        return artworkImageUrls;
    }
}
