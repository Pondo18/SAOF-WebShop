package de.hdbw.webshop.dto.artwork;

import de.hdbw.webshop.model.artwork.ImageEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageResponseDTO {
    private String uuid;
    private String fileType;
    private long size;

    public ImageResponseDTO(ImageEntity image) {
        setUuid(image.getUuid());
        setFileType(image.getFileType());
        setSize(image.getSize());
    }
}