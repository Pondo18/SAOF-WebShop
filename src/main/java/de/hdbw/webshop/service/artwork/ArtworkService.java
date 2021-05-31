package de.hdbw.webshop.service.artwork;

import de.hdbw.webshop.dto.artwork.ArtworkForListViewDTO;
import de.hdbw.webshop.dto.artwork.EditMyArtworkDTO;
import de.hdbw.webshop.exception.exceptions.ArtworkNotFoundException;
import de.hdbw.webshop.model.artwork.entity.ArtworkEntity;
import de.hdbw.webshop.model.users.entity.ArtistEntity;
import de.hdbw.webshop.model.users.entity.RegisteredUsersEntity;
import de.hdbw.webshop.repository.artwork.ArtworkRepository;
import de.hdbw.webshop.service.user.RegisteredUserService;
import de.hdbw.webshop.util.string.NameHelper;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@CommonsLog
public class ArtworkService {

    private final ArtworkRepository artworkRepository;
    private final ArtworkDTOService artworkDTOService;
    private final RegisteredUserService registeredUserService;
    private final ImageService imageService;
    private final NameHelper nameHelper;

    @Autowired
    public ArtworkService(ArtworkRepository artworkRepository, ArtworkDTOService artworkDTOService, RegisteredUserService registeredUserService, ImageService imageService, NameHelper nameHelper) {
        this.artworkRepository = artworkRepository;
        this.artworkDTOService = artworkDTOService;
        this.registeredUserService = registeredUserService;
        this.imageService = imageService;
        this.nameHelper = nameHelper;
    }

    public long getArtworkIdByArtworkName(String artworkName) {
        return artworkRepository.findArtworkIdByGeneratedArtworkName(artworkName).orElseThrow(
                ArtworkNotFoundException::new
        );
    }

    public ArtworkEntity getArtworkEntityByGeneratedArtworkName (String generatedArtworkName) {
        return artworkRepository.findByGeneratedArtworkName(generatedArtworkName).orElseThrow(
                ArtworkNotFoundException::new
        );
    }

    public List<ArtworkEntity> changeAmountOfAvailableArtworks(List<ArtworkEntity> artworksToChange) {
        return artworksToChange.stream().peek(artworkEntity -> {
            artworkEntity.setAvailable(artworkEntity.getAvailable()-1);
        }).collect(Collectors.toList());
    }

    public void saveAll(List<ArtworkEntity> artworksToSave) {
        artworkRepository.saveAll(artworksToSave);
    }

    public List<ArtworkForListViewDTO> findAllArtworksByArtist(ArtistEntity artistEntity) {
        List<ArtworkEntity> artworks = artworkRepository.findAllByArtistAndAvailable(artistEntity, 1);
        return artworkDTOService.getAllArtworksForListViewByArtworkEntity(artworks);
    }

    public ArtworkEntity createNewArtwork (EditMyArtworkDTO editMyArtworkDTO, Authentication authentication) {
        ArtistEntity artist = registeredUserService.findRegisteredUserEntityByAuthentication(authentication).getArtistEntity();
        ArtworkEntity artworkEntity = artworkDTOService.getArtworkEntityByCreateNewArtworkDTO(editMyArtworkDTO, artist);
        log.info("Artist: " + artist.getRegisteredUserEntity().getEmail() + " is adding a new Artwork: " + artworkEntity.getGeneratedArtworkName());
        return artworkRepository.save(artworkEntity);
    }

    public boolean existsByArtistAndGeneratedArtworkName(RegisteredUsersEntity currentUser, String generatedArtworkName) {
        return artworkRepository.existsByGeneratedArtworkNameAndArtist_RegisteredUserEntity(generatedArtworkName, currentUser);
    }

    public long removeArtworkWithGeneratedArtworkName(String generatedArtworkName, Authentication authentication) {
        RegisteredUsersEntity currentUser = registeredUserService.findRegisteredUserEntityByAuthentication(authentication);
        if (artworkRepository.existsByGeneratedArtworkNameAndArtist_RegisteredUserEntity(generatedArtworkName, currentUser)) {
            log.info("Removing Artwork with the generatedArtworkName: " + generatedArtworkName
                    + " by the artist with the email: " + currentUser.getEmail());
            return artworkRepository.deleteByGeneratedArtworkName(generatedArtworkName);
        } else {
            log.info("Couldn't delete Artwork: " + generatedArtworkName
            + " because its not from the deleter: " + currentUser.getEmail());
            throw new ArtworkNotFoundException();
        }
    }

    public ArtworkEntity editArtwork(EditMyArtworkDTO editMyArtworkDTO, Authentication authentication, String oldGeneratedArtworkName) {
        RegisteredUsersEntity currentUser = registeredUserService.findRegisteredUserEntityByAuthentication(authentication);
        if (existsByArtistAndGeneratedArtworkName(currentUser, oldGeneratedArtworkName)) {
            log.info("Editing Artwork with the generatedArtworkName: " + oldGeneratedArtworkName
                    + " by the artist with the email: " + currentUser.getEmail());
            ArtworkEntity oldArtwork = getArtworkEntityByGeneratedArtworkName(oldGeneratedArtworkName);
            return artworkRepository.save(editArtworkEntityByEditArtworkDTO(editMyArtworkDTO, oldArtwork));
        } else {
            log.info("Couldn't delete Artwork: " + oldGeneratedArtworkName
                    + " because its not from the deleter: " + currentUser.getEmail());
            throw new ArtworkNotFoundException();
        }
    }

    public ArtworkEntity editArtworkEntityByEditArtworkDTO(EditMyArtworkDTO artworkChanges, ArtworkEntity existingArtwork) {
        if (!artworkChanges.getArtworkName().equals(existingArtwork.getArtworkName())) {
            existingArtwork.setArtworkName(artworkChanges.getArtworkName());
            existingArtwork.setGeneratedArtworkName(nameHelper.generateArtworkName(artworkChanges.getArtworkName()));
        }
        if (!artworkChanges.getArtworkDescription().equals(existingArtwork.getDescription())) {
            existingArtwork.setDescription(artworkChanges.getArtworkDescription());
        }
        if (!(artworkChanges.getArtworkPrice() == existingArtwork.getPrice())) {
            existingArtwork.setPrice(artworkChanges.getArtworkPrice());
        }
        return imageService.changeImagesIfNecessary(artworkChanges, existingArtwork);
    }




    public EditMyArtworkDTO getEditMyArtworkDTOIfExisting(String generatedArtworkName, RegisteredUsersEntity currentUser) {
        ArtworkEntity artworkEntity = artworkRepository.findByGeneratedArtworkNameAndArtist_RegisteredUserEntity(generatedArtworkName, currentUser)
                .orElse(null);
        if (artworkEntity!=null) {
            return artworkDTOService.getEditMyArtworkDTOByArtworkEntity(artworkEntity);
        } else {
            return null;
        }
    }
}
