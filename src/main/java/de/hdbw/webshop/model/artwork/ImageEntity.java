package de.hdbw.webshop.model.artwork;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.persistence.*;

import de.hdbw.webshop.util.images.FileNameHelper;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    private String fileName;
    private String fileType;
    private long size;
    private String uuid;
    private String systemName;
    private int position;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "artwork_id", referencedColumnName = "id")
    private ArtworkEntity artwork;

    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] data;

    public ImageEntity(String fileName, String fileType, long size, String uuid, String systemName, byte[] data) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.size = size;
        this.uuid = uuid;
        this.systemName = systemName;
        this.data = data;
    }

    @Transient
    public static ImageEntity build() {
        String uuid = UUID.randomUUID().toString();
        ImageEntity image = new ImageEntity();
        image.setUuid(uuid);
        image.setSystemName("default");
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
        InputStream is = getResourceFileAsInputStream("notfound.jpg");
        String fileType = "image/jpeg";
        byte[] bdata = FileCopyUtils.copyToByteArray(is);
        ImageEntity image = new ImageEntity(null, fileType, 0, null, null, bdata);
        return image;
    }

    /**
     * Generate scaled no context image with `notfound.jpg` image in asset with
     * given width and height.
     *
     * @param width  scale width
     * @param height scale height
     * @return create scaled default image.
     */
    @Transient
    public static ImageEntity defaultImage(int width, int height) throws Exception {
        ImageEntity defaultImage = defaultImage();
        defaultImage.scale(width, height);
        return defaultImage;
    }

    /**
     * Generate scaled no context image with `notfound.jpg` image in asset with
     * given width and height.
     *
     * @param file   multipartfile data to build.
     * @param helper filenamehelper class to generate name.
     * @return return new Image class related with file.
     */
    @Transient
    public static ImageEntity buildImage(MultipartFile file, FileNameHelper helper, int position) {
        String fileName = helper.generateDisplayName(file.getOriginalFilename());

        ImageEntity image = ImageEntity.build();
        image.setFileName(fileName);
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
