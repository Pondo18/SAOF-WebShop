package de.hdbw.webshop.model.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class RegisteredUser {
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
    private AllUsers allUsers;

    @OneToOne(mappedBy = "registeredUser")
    private UserPassword userPassword;

}
