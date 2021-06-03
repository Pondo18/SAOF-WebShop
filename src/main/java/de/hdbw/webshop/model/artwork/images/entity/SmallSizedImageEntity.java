package de.hdbw.webshop.model.artwork.images.entity;

import de.hdbw.webshop.model.artwork.images.Image;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;

@Entity
@Table(name = "image_small")
@Data
@NoArgsConstructor
public class SmallSizedImageEntity implements Image {

    @Id
    @Column(nullable = false, updatable = false)
    private String uuid;
    private long size;
    private byte[] data;

    @OneToOne
    @JoinColumn(name = "default_id", referencedColumnName = "id")
    private DefaultImageEntity defaultImage;


    public SmallSizedImageEntity(DefaultImageEntity defaultImage, String uuid) {
        this.uuid = uuid;
        this.defaultImage = defaultImage;
    }

    public SmallSizedImageEntity(byte[] data) {
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
