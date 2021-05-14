package de.hdbw.webshop.model.users.entity;

import de.hdbw.webshop.model.artwork.ShoppingCartEntity;
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

    @OneToMany(mappedBy = "allUsersEntity")
    private List<ShoppingCartEntity> shoppingCartEntity;
}
