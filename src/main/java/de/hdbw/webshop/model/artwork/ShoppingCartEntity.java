package de.hdbw.webshop.model.artwork;
import de.hdbw.webshop.model.users.entity.AllUsersEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "shopping_cart")
@Data
@NoArgsConstructor
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

    public ShoppingCartEntity(AllUsersEntity allUsersEntity, ArtworkEntity artworkEntity) {
        this.allUsersEntity = allUsersEntity;
        this.artworkEntity = artworkEntity;
    }
}
