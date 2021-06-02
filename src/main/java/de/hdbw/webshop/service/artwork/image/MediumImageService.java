package de.hdbw.webshop.service.artwork.image;

import de.hdbw.webshop.exception.exceptions.ImageNotFoundException;
import de.hdbw.webshop.model.artwork.images.Image;
import de.hdbw.webshop.model.artwork.images.entity.DefaultImageEntity;
import de.hdbw.webshop.model.artwork.images.entity.MediumSizedImageEntity;
import de.hdbw.webshop.repository.artwork.image.MediumImageRepository;
import de.hdbw.webshop.util.string.NameHelper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.Transient;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MediumImageService implements SizedImageService {

    private final ImageHelperService imageHelperService;
    private final MediumImageRepository mediumImageRepository;
    private final NameHelper nameHelper;

    public MediumImageService(ImageHelperService imageHelperService, MediumImageRepository mediumImageRepository, NameHelper nameHelper) {
        this.imageHelperService = imageHelperService;
        this.mediumImageRepository = mediumImageRepository;
        this.nameHelper = nameHelper;
    }

    @Transient
    public MediumSizedImageEntity buildMediumImageEntity (DefaultImageEntity defaultImageEntity) {
        try {
            MediumSizedImageEntity mediumSizedImageEntity = new MediumSizedImageEntity(defaultImageEntity, nameHelper.getUnusedUuid());
            imageHelperService.scaleImage(mediumSizedImageEntity, mediumSizedImageEntity.getWidth(), mediumSizedImageEntity.getHeight());
            return mediumSizedImageEntity;
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Couldn't create smallImage By defaultSizedImage",
                    e
            );
        }
    }


    public List<MediumSizedImageEntity> getMediumSizedImagesByDefaultImages (List<DefaultImageEntity> defaultImages) {
        return defaultImages.stream().map(
                this::buildMediumImageEntity
        ).collect(Collectors.toList());
    }


    public boolean mediumImageExists(String uuid) {
        return mediumImageRepository.existsById(uuid);
    }

    @Override
    public Image getByUuid(String uuid) {
        return mediumImageRepository.findById(uuid).orElseThrow(ImageNotFoundException::new);
    }

    @Override
    public Image getDefaultImage() throws Exception {
        InputStream is = imageHelperService.getResourceFileAsInputStream("static/images/upload_image.jpg");
        byte[] bdata = FileCopyUtils.copyToByteArray(is);
        MediumSizedImageEntity image = new MediumSizedImageEntity(bdata);
        return image;
    }
}
