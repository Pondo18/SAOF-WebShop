package de.hdbw.webshop.model.artwork.images;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageMultipartWrapper {

    private MultipartFile multipartFile;
    private int position;

    public ImageMultipartWrapper(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }

    public String getImageBase64() {
        CustomMultipartFile asCustom = (CustomMultipartFile) this.multipartFile;
        try {
            return asCustom.getImageBase64();
        } catch (NullPointerException e) {
            return null;
        }
    }

    public String getImageContentType() {
        try {
            return this.multipartFile.getContentType();
        } catch (Exception e) {
            return null;
        }
    }
}
