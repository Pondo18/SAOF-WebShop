package de.hdbw.webshop.service.artwork.image;

import de.hdbw.webshop.exception.exceptions.ImageNotFoundException;
import de.hdbw.webshop.model.artwork.images.Image;
import de.hdbw.webshop.model.artwork.images.entity.BigSizedImageEntity;
import de.hdbw.webshop.model.artwork.images.entity.DefaultImageEntity;
import de.hdbw.webshop.repository.artwork.image.BigImageRepository;
import de.hdbw.webshop.util.string.NameHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.Transient;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BigImageService implements SizedImageService {

    private final ImageHelperService imageHelperService;
    private final BigImageRepository bigImageRepository;
    private final NameHelper nameHelper;
    @Value("${image.big.width}")
    private int width;
    @Value("${image.big.height}")
    private int height;

    public BigImageService(ImageHelperService imageHelperService, BigImageRepository bigImageRepository, NameHelper nameHelper) {
        this.imageHelperService = imageHelperService;
        this.bigImageRepository = bigImageRepository;
        this.nameHelper = nameHelper;
    }

    @Transient
    public BigSizedImageEntity buildBigSizedImageEntity (DefaultImageEntity defaultImageEntity) {
        try {
            BigSizedImageEntity bigSizedImageEntity = new BigSizedImageEntity(defaultImageEntity, nameHelper.getUnusedUuid());
            imageHelperService.scaleImage(bigSizedImageEntity, width, height);
            return bigSizedImageEntity;
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Couldn't create smallImage By defaultSizedImage",
                    e
            );
        }
    }


    public boolean bigImageExists(String uuid) {
        return bigImageRepository.existsById(uuid);
    }

    public List<String> getAllBigImageUuidsByArtworkAndOrderByPosition(long artworkId) {
        return bigImageRepository.findAllBigImageUuidsByArtworkAndOrderByPosition(artworkId);
    }

    @Override
    public Image getByUuid(String uuid) {
        return bigImageRepository.findById(uuid).orElseThrow(ImageNotFoundException::new);
    }

    @Override
    public Image getDefaultImage() throws Exception {
        return null;
    }
}
