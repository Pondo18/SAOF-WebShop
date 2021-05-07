package de.hdbw.webshop.model.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "artist")
@Data
@NoArgsConstructor
public class ArtistEntity implements User {

    @Id
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private RegisteredUserEntity registeredUserEntity;

    @Override
    public String getEmail() {
        return registeredUserEntity.getEmail();
    }

    @Override
    public boolean isEnabled() {
        return registeredUserEntity.isEnabled();
    }


}
