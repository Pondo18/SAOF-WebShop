package de.hdbw.webshop.model.auth;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;
    private String firstName;
    private String secondName;
    @Column(unique = true)
    private String email;
    private String password;
    private boolean enabled;

    @OneToMany(mappedBy = "user")
    private List<UserRoleEntity> userRoles;


    public UserEntity(String firstName, String secondName, String email, String password, boolean enabled) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
    }
}
