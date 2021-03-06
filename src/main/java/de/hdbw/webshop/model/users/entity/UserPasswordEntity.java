package de.hdbw.webshop.model.users.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_password")
@Data
@NoArgsConstructor
public class UserPasswordEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private long id;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private RegisteredUsersEntity registeredUser;
    private String passwordHash;

    public UserPasswordEntity(RegisteredUsersEntity registeredUser, String passwordHash) {
        this.registeredUser = registeredUser;
        this.passwordHash = passwordHash;
    }
}
