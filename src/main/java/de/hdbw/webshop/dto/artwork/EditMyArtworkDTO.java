package de.hdbw.webshop.dto.artwork;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
public class EditMyArtworkDTO {

    private String generatedArtworkName;
    @NotNull
    private String artworkName;
    private String artworkDescription;
    @NotNull
    private double artworkPrice;

    @Lob
    private List<MultipartFile> images;
//    private List<String> oldImageUrls;

    @Lob
    private MultipartFile firstImage;
    @Lob
    private MultipartFile secondImage;
    @Lob
    private MultipartFile thirdImage;
    @Lob
    private MultipartFile forthImage;

    public EditMyArtworkDTO(String generatedArtworkName, @NotNull String artworkName, String artworkDescription, @NotNull double artworkPrice) {
        this.generatedArtworkName = generatedArtworkName;
        this.artworkName = artworkName;
        this.artworkDescription = artworkDescription;
        this.artworkPrice = artworkPrice;
    }
}
