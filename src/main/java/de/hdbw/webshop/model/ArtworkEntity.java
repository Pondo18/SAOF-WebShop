package de.hdbw.webshop.model;

import javax.persistence.*;

@Entity
@Table(name = "artwork")
public class ArtworkEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    @Column(nullable = false)
    private String ArtworkName;
    private String Artist;
    @Column(nullable = false)
    private double price;

    public ArtworkEntity(Long id, String artworkName, String artist, double price) {
        this.id = id;
        ArtworkName = artworkName;
        Artist = artist;
        this.price = price;
    }

    public ArtworkEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArtworkName() {
        return ArtworkName;
    }

    public void setArtworkName(String artworkName) {
        ArtworkName = artworkName;
    }

    public String getArtist() {
        return Artist;
    }

    public void setArtist(String artist) {
        Artist = artist;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    @Override
    public String toString() {
        return "Artwork{" +
                "id=" + id +
                ", ArtworkName='" + ArtworkName + '\'' +
                ", Artist='" + Artist + '\'' +
                ", price=" + price +
                '}';
    }
}
