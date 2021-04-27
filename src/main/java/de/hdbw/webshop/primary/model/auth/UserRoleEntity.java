package de.hdbw.webshop.primary.model.auth;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "user_role")
@Data
@NoArgsConstructor
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
}
