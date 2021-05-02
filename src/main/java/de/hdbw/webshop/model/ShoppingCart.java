package de.hdbw.webshop.model;

import de.hdbw.webshop.model.artwork.ArtworkEntity;
import de.hdbw.webshop.model.auth.UserEntity;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class ShoppingCart {

    @Id
    @Column(nullable = false, updatable = false)
    private long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity userEntity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "artwork_id", referencedColumnName = "id")
    private ArtworkEntity artworkEntity;

    private int amount;

}
