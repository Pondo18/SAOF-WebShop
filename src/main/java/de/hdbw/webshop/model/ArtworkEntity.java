package de.hdbw.webshop.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    private String artist;
    @Column(nullable = false)
    private double price;

    public ArtworkEntity(String artworkName, String artist, double price) {
        this.artworkName = artworkName;
        this.artist = artist;
        this.price = price;
    }
}
