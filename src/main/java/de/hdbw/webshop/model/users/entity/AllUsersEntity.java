package de.hdbw.webshop.model.users.entity;

import de.hdbw.webshop.model.artwork.artworks.entity.BoughtArtworkEntity;
import de.hdbw.webshop.model.artwork.artworks.entity.ShoppingCartEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "all_users")
@Data
@NoArgsConstructor
public class AllUsersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @OneToOne(mappedBy = "allUsers")
    private RegisteredUsersEntity registeredUser;

    @OneToOne(mappedBy = "allUsers")
    private UnregisteredUserEntity unregisteredUser;

    @OneToMany(mappedBy = "allUsersEntity", cascade = CascadeType.ALL)
    private List<ShoppingCartEntity> shoppingCartEntity;

    @OneToMany(mappedBy = "allUsersEntity")
    private List<BoughtArtworkEntity> boughtArtworks;
}
