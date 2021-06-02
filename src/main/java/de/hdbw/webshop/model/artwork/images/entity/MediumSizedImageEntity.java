package de.hdbw.webshop.model.artwork.images.entity;

import de.hdbw.webshop.model.artwork.images.Image;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "image_medium")
@Data
@NoArgsConstructor
public class MediumSizedImageEntity implements Image {

    @Id
    @Column(nullable = false, updatable = false)
    private String uuid;
    private long size;
    private byte[] data;
    private final int width = 300;
    private final int height = 300;

    @OneToOne
    @JoinColumn(name = "default_id", referencedColumnName = "id")
    private DefaultImageEntity defaultImage;

    public MediumSizedImageEntity(DefaultImageEntity defaultImage, String uuid) {
        this.uuid = uuid;
        this.defaultImage = defaultImage;
    }

    public MediumSizedImageEntity(byte[] data) {
        this.data = data;
    }

    @Override
    public String getFileType() {
        return defaultImage.getFileType();
    }


    @Override
    public int getPosition() {
        return defaultImage.getPosition();
    }

}
