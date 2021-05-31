package de.hdbw.webshop.dto.artwork;

import de.hdbw.webshop.model.artwork.CustomMultipartFile;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

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
    private MultipartFile firstImage;
    @Lob
    private MultipartFile secondImage;
    @Lob
    private MultipartFile thirdImage;
    @Lob
    private MultipartFile forthImage;

    public EditMyArtworkDTO(@NotNull String artworkName, String artworkDescription, @NotNull double artworkPrice) {
        this.artworkName = artworkName;
        this.artworkDescription = artworkDescription;
        this.artworkPrice = artworkPrice;
    }

    public String getFirstImageBase64() {
        CustomMultipartFile asCustom = (CustomMultipartFile) this.firstImage;
        try {
            return asCustom.getImageBase64();
        } catch (NullPointerException e) {
            return null;
        }
    }

    public String getSecondImageBase64() {
        CustomMultipartFile asCustom = (CustomMultipartFile) this.secondImage;
        try {
            return asCustom.getImageBase64();
        } catch (NullPointerException e) {
            return null;
        }
    }

    public String getThirdImageBase64() {
        CustomMultipartFile asCustom = (CustomMultipartFile) this.thirdImage;
        try {
            return asCustom.getImageBase64();
        } catch (NullPointerException e) {
            return null;
        }
    }

    public String getForthImageBase64() {
        CustomMultipartFile asCustom = (CustomMultipartFile) this.forthImage;
        try {
            return asCustom.getImageBase64();
        } catch (NullPointerException e) {
            return null;
        }
    }

}
