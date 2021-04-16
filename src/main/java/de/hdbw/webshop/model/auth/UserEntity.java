package de.hdbw.webshop.model.auth;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;
    private String username;
    private String password;
    private boolean enabled;

    @OneToOne(mappedBy = "user")
    private UserRoleEntity userRole;
}
