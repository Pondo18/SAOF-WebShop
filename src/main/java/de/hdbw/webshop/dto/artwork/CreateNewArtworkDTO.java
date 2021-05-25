package de.hdbw.webshop.dto.artwork;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CreateNewArtworkDTO {

    @NotNull
    private String artworkName;
    private String artworkDescription;
    @NotNull
    private double artworkPrice;

    @Lob
    private List<MultipartFile> images;
}
