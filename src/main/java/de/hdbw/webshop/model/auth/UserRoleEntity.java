package de.hdbw.webshop.model.auth;

import javax.persistence.*;

@Entity
@Table(name = "user_role")
public class UserRoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    @Enumerated(EnumType.ORDINAL)
    private RolesEntity role;

    public UserRoleEntity(Long id, UserEntity user) {
        this.id = id;
        this.user = user;
    }

    public UserRoleEntity() {

    }
}
