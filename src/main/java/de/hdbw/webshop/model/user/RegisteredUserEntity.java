package de.hdbw.webshop.model.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "registered_user")
@Data
@NoArgsConstructor
public class RegisteredUserEntity implements User, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;
    private String email;
    private String firstName;
    private String secondName;
    private boolean enabled;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private AllUsersEntity allUsers;

    @OneToOne(mappedBy = "registeredUser")
    private UserPasswordEntity userPassword;

    @OneToOne(mappedBy = "registeredUserEntity")
    private ArtistEntity artistEntity;
}
