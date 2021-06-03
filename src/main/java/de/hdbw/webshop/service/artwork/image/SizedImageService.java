package de.hdbw.webshop.service.artwork.image;

import de.hdbw.webshop.model.artwork.images.Image;

public interface SizedImageService {
    Image getByUuid(String uuid);
    Image getDefaultImage() throws Exception;
}
