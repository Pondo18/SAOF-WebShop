package de.hdbw.webshop.model.users.entity;

import de.hdbw.webshop.model.users.RegisteredUsers;
import de.hdbw.webshop.model.users.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "registered_user")
@Data
@NoArgsConstructor
public class RegisteredUsersEntity implements RegisteredUsers, User, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;
    private String email;
    private String firstName;
    private String secondName;
    private boolean enabled;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private AllUsersEntity allUsers;

    @OneToOne(mappedBy = "registeredUser")
    private UserPasswordEntity userPassword;

    @OneToOne(mappedBy = "registeredUserEntity")
    private ArtistEntity artistEntity;

    public RegisteredUsersEntity(String email, String firstName, String secondName,
                                 boolean enabled, AllUsersEntity allUsers) {
        this.email = email;
        this.firstName = firstName;
        this.secondName = secondName;
        this.enabled = enabled;
        this.allUsers = allUsers;
    }

    @Override
    public long getUserId() {
        return allUsers.getId();
    }
}
