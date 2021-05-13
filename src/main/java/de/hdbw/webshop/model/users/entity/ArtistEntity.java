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
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private RegisteredUsersEntity registeredUserEntity;

    @Override
    public String getEmail() {
        return registeredUserEntity.getEmail();
    }

    @Override
    public boolean isEnabled() {
        return registeredUserEntity.isEnabled();
    }


}
