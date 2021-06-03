package de.hdbw.webshop.model.artwork.images;

import de.hdbw.webshop.model.artwork.images.entity.DefaultImageEntity;

public interface Image {

    String getUuid();
    byte[] getData();
    String getFileType();
    int getPosition();
    void setData(byte[] data);
    DefaultImageEntity getDefaultImage();
    void setSize(long size);
}
