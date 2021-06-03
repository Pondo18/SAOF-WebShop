package de.hdbw.webshop.model.artwork.images.entity;

import java.io.IOException;

import javax.persistence.*;

import de.hdbw.webshop.model.artwork.artworks.entity.ArtworkEntity;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Entity
@Table(name = "artwork_images")
@Data
@NoArgsConstructor
public class DefaultImageEntity {
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String fileType;
    private long size;
    private int position;

    @ManyToOne
    @JoinColumn(name = "artwork_id", referencedColumnName = "id")
    private ArtworkEntity artwork;

    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] data;

    @OneToOne(mappedBy = "defaultImage", cascade = CascadeType.ALL)
    private SmallSizedImageEntity smallSizedImageEntity;

    @OneToOne(mappedBy = "defaultImage", cascade = CascadeType.ALL)
    private MediumSizedImageEntity mediumSizedImageEntity;

    @OneToOne(mappedBy = "defaultImage", cascade = CascadeType.ALL)
    private BigSizedImageEntity bigSizedImageEntity;

    public DefaultImageEntity(String fileType, long size, byte[] data) {
        this.fileType = fileType;
        this.size = size;
        this.data = data;
    }

    @Transient
    public void setFiles(MultipartFile file) {
        setFileType(file.getContentType());
        setSize(file.getSize());
    }

    @Transient
    public static DefaultImageEntity buildImage(MultipartFile file, int position, ArtworkEntity artworkEntity) {
        DefaultImageEntity image = new DefaultImageEntity();
        image.setFiles(file);
        image.setPosition(position);
        image.setArtwork(artworkEntity);
        try {
            image.setData(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

}
