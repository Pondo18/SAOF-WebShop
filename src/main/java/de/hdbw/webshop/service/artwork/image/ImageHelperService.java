package de.hdbw.webshop.service.artwork.image;

import de.hdbw.webshop.model.artwork.images.Image;
import de.hdbw.webshop.model.artwork.images.entity.DefaultImageEntity;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.persistence.Transient;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

@Service
public class ImageHelperService {

    @Transient
    public void scaleImage (Image image, int width, int height) throws Exception {
        ByteArrayInputStream in = new ByteArrayInputStream(image.getDefaultImage().getData());
        try {
            BufferedImage img = ImageIO.read(in);

            java.awt.Image scaledImage = img.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
            BufferedImage imgBuff = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            imgBuff.getGraphics().drawImage(scaledImage, 0, 0, new Color(0, 0, 0), null);
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ImageIO.write(imgBuff, "jpg", buffer);
            image.setData(buffer.toByteArray());
            image.setSize(buffer.size());

        } catch (Exception e) {
            throw new Exception("IOException in scale");

        }
    }


    public InputStream getResourceFileAsInputStream(String fileName) {
        ClassLoader classLoader = DefaultImageEntity.class.getClassLoader();
        return classLoader.getResourceAsStream(fileName);
    }
}
