package de.hdbw.webshop.model.user;

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

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private RegisteredUserEntity registeredUser;
    private String passwordHash;
}
