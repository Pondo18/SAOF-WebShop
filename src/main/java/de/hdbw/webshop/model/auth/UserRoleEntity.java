package de.hdbw.webshop.model.auth;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Table(name = "user_role")
public class UserRoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    @Enumerated(EnumType.ORDINAL)
    private RolesEntity role;

    public UserRoleEntity(UserEntity user, RolesEntity role) {
        this.user = user;
        this.role = role;
    }

    public UserRoleEntity(UserEntity userEntity, Optional<RolesEntity> rolesEntity) {

    }
}
