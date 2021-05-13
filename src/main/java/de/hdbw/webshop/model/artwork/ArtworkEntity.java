package de.hdbw.webshop.model.artwork;

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
    private String artist;
    private String description;
    @Column(nullable = false)
    private double price;

    @OneToMany(mappedBy = "artwork")
    private List<ImageEntity> images;

    @OneToMany(mappedBy = "artworkEntity")
    private List<ShoppingCartEntity> shoppingCartEntities;
}
