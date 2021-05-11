package de.hdbw.webshop.model.artwork;

import de.hdbw.webshop.model.artwork.ArtworkEntity;
import de.hdbw.webshop.model.users.AllUsersEntity;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class ShoppingCartEntity {

    @Id
    @Column(nullable = false, updatable = false)
    private long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private AllUsersEntity allUsersEntity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "artwork_id", referencedColumnName = "id")
    private ArtworkEntity artworkEntity;

    private int amount;

}
