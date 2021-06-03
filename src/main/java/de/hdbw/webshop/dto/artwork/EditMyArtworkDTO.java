package de.hdbw.webshop.dto.artwork;

import de.hdbw.webshop.model.artwork.images.ImageMultipartWrapper;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
public class EditMyArtworkDTO {

    @NotNull
    private String artworkName;
    private String artworkDescription;
    @NotNull
    private double artworkPrice;

    @Lob
    private List<ImageMultipartWrapper> images;

    public EditMyArtworkDTO(@NotNull String artworkName, String artworkDescription, @NotNull double artworkPrice, List<ImageMultipartWrapper> images) {
        this.artworkName = artworkName;
        this.artworkDescription = artworkDescription;
        this.artworkPrice = artworkPrice;
        this.images = images;
    }

}
