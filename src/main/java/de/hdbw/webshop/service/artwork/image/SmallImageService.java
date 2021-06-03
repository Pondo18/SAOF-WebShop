package de.hdbw.webshop.service.artwork.image;

import de.hdbw.webshop.exception.exceptions.ImageNotFoundException;
import de.hdbw.webshop.model.artwork.images.Image;
import de.hdbw.webshop.model.artwork.images.entity.DefaultImageEntity;
import de.hdbw.webshop.model.artwork.images.entity.SmallSizedImageEntity;
import de.hdbw.webshop.repository.artwork.image.SmallImageRepository;
import de.hdbw.webshop.util.string.NameHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.Transient;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SmallImageService implements SizedImageService {

    private final ImageHelperService imageHelperService;
    private final SmallImageRepository smallImageRepository;
    private final NameHelper nameHelper;
    @Value("${image.small.width}")
    private int width;
    @Value("${image.small.height}")
    private int height;

    public SmallImageService(ImageHelperService imageHelperService, SmallImageRepository smallImageRepository, NameHelper nameHelper) {
        this.imageHelperService = imageHelperService;
        this.smallImageRepository = smallImageRepository;
        this.nameHelper = nameHelper;
    }

    @Transient
    public SmallSizedImageEntity buildSmallImageEntity (DefaultImageEntity defaultImageEntity) {
        try {
            SmallSizedImageEntity smallSizedImageEntity = new SmallSizedImageEntity(defaultImageEntity, nameHelper.getUnusedUuid());
            imageHelperService.scaleImage(smallSizedImageEntity, width, height);
            return smallSizedImageEntity;
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Couldn't create smallImage By defaultSizedImage",
                    e
            );
        }
    }

    public List<SmallSizedImageEntity> getSmallSizedImageEntityByDefaultImages (List<DefaultImageEntity> defaultImages) {
        return defaultImages.stream().map(
                this::buildSmallImageEntity
        ).collect(Collectors.toList());
    }

    public boolean smallImageExists(String uuid) {
        return smallImageRepository.existsById(uuid);
    }

    @Override
    public Image getByUuid(String uuid) {
        return smallImageRepository.findById(uuid).orElseThrow(ImageNotFoundException::new);
    }

    @Override
    @Transient
    public Image getDefaultImage() throws Exception {
        InputStream is = imageHelperService.getResourceFileAsInputStream("static/images/image_missing.png");
        byte[] bdata = FileCopyUtils.copyToByteArray(is);
        SmallSizedImageEntity image = new SmallSizedImageEntity(bdata);
        return image;
    }
}
