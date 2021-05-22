package de.hdbw.webshop.model.artwork;

import de.hdbw.webshop.model.users.entity.ArtistEntity;
import de.hdbw.webshop.util.string.NameHelper;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "artwork")
@Data
@NoArgsConstructor
public class ArtworkEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    @Column(nullable = false)
    private String artworkName;
    @Column(nullable = false, unique = true)
    private String generatedArtworkName;

    @ManyToOne
    @JoinColumn(name = "artist_id", referencedColumnName = "id")
    private ArtistEntity artist;
    private String description;
    @Column(nullable = false)
    private double price;
    private int available;

    @OneToMany(mappedBy = "artwork", cascade = CascadeType.ALL)
    private List<ImageEntity> images;

    @OneToMany(mappedBy = "artworkEntity")
    private List<ShoppingCartEntity> shoppingCartEntities;

    @OneToMany(mappedBy = "artworkEntity")
    private List<BoughtArtworkEntity> boughtArtworkEntities;


    public ArtworkEntity(String artworkName, ArtistEntity artist, String description, double price) {
        this.artworkName = artworkName;
        this.artist = artist;
        this.description = description;
        this.price = price;
        this.available = 1;
    }
}
