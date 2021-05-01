package de.hdbw.webshop.model.artwork;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.persistence.*;

import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Entity
@Table(name = "artwork_images")
@Data
@NoArgsConstructor
public class ImageEntity {
    @Id
    @Column(nullable = false, updatable = false)
    private String uuid;
    private String fileType;
    private long size;
    private int position;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "artwork_id", referencedColumnName = "id")
    private ArtworkEntity artwork;

    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] data;

    public ImageEntity(String fileType, long size, String uuid, byte[] data) {
        this.fileType = fileType;
        this.size = size;
        this.uuid = uuid;
        this.data = data;
    }

    @Transient
    public static ImageEntity build() {
        String uuid = UUID.randomUUID().toString();
        ImageEntity image = new ImageEntity();
        image.setUuid(uuid);
        return image;
    }

    @Transient
    public void setFiles(MultipartFile file) {
        setFileType(file.getContentType());
        setSize(file.getSize());
    }

    /**
     * Scale image data with given width and height.
     *
     * @param width  scale width
     * @param height scale height
     * @return scaled image byte array and change to class data.
     */
    @Transient
    public byte[] scale(int width, int height) throws Exception {

        if (width == 0 || height == 0)
            return data;

        ByteArrayInputStream in = new ByteArrayInputStream(data);

        try {
            BufferedImage img = ImageIO.read(in);

            java.awt.Image scaledImage = img.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
            BufferedImage imgBuff = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            imgBuff.getGraphics().drawImage(scaledImage, 0, 0, new Color(0, 0, 0), null);
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            ImageIO.write(imgBuff, "jpg", buffer);
            setData(buffer.toByteArray());
            return buffer.toByteArray();

        } catch (Exception e) {
            throw new Exception("IOException in scale");
        }
    }

    /**
     * @param fileName - filename of the resources.
     *
     * @return inputstream for image
     * */
    private static InputStream getResourceFileAsInputStream(String fileName) {
        ClassLoader classLoader = ImageEntity.class.getClassLoader();
        return classLoader.getResourceAsStream(fileName);
    }

    /**
     * Generate no context image with `notfound.jpg` image in asset.
     *
     * @return create default image.
     */
    @Transient
    public static ImageEntity defaultImage() throws Exception {
        InputStream is = getResourceFileAsInputStream("src/main/resources/static/images/image_missing.png");
        String fileType = "image/png";
        byte[] bdata = FileCopyUtils.copyToByteArray(is);
        ImageEntity image = new ImageEntity(fileType, 0, null, bdata);
        return image;
    }

    @Transient
    public static ImageEntity buildImage(MultipartFile file, int position) {
        ImageEntity image = ImageEntity.build();
        image.setFiles(file);
        image.setPosition(position);

        try {
            image.setData(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

}
