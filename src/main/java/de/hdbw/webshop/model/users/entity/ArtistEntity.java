package de.hdbw.webshop.model.users.entity;

import de.hdbw.webshop.model.users.RegisteredUsers;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "artist")
@Data
@NoArgsConstructor
public class ArtistEntity implements RegisteredUsers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;
    private String country;
    private String domicile;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private RegisteredUsersEntity registeredUserEntity;

    public ArtistEntity(String country, String domicile, RegisteredUsersEntity registeredUserEntity) {
        this.country = country;
        this.domicile = domicile;
        this.registeredUserEntity = registeredUserEntity;
    }

    @Override
    public String getEmail() {
        return registeredUserEntity.getEmail();
    }

    @Override
    public boolean isEnabled() {
        return registeredUserEntity.isEnabled();
    }



}
