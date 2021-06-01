package de.hdbw.webshop.dto.artwork;

import de.hdbw.webshop.model.artwork.entity.ImageMultipartWrapper;
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
    private ImageMultipartWrapper firstImage;
    @Lob
    private ImageMultipartWrapper secondImage;
    @Lob
    private ImageMultipartWrapper thirdImage;
    @Lob
    private ImageMultipartWrapper forthImage;

    @Lob
    private List<ImageMultipartWrapper> images;

    public EditMyArtworkDTO(@NotNull String artworkName, String artworkDescription, @NotNull double artworkPrice) {
        this.artworkName = artworkName;
        this.artworkDescription = artworkDescription;
        this.artworkPrice = artworkPrice;
    }

}
