package de.hdbw.webshop.model.artwork.images.entity;


import de.hdbw.webshop.model.artwork.images.Image;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "image_big")
@Data
@NoArgsConstructor
public class BigSizedImageEntity implements Image {

    @Id
    @Column(nullable = false, updatable = false)
    private String uuid;
    private long size;
    private byte[] data;

    @OneToOne
    @JoinColumn(name = "default_id", referencedColumnName = "id")
    private DefaultImageEntity defaultImage;


    public BigSizedImageEntity(DefaultImageEntity defaultImage, String uuid) {
        this.uuid = uuid;
        this.defaultImage = defaultImage;
    }

    @Override
    public String getFileType() {
        return defaultImage!=null ?
                defaultImage.getFileType():"image/jpg";
    }


    @Override
    public int getPosition() {
        return defaultImage.getPosition();
    }
}
