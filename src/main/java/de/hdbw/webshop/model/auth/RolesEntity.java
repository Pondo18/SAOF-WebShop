package de.hdbw.webshop.model.auth;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class RolesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;
    @Enumerated(EnumType.ORDINAL)
    private Roles role;
    @OneToOne(mappedBy = "role")
    private UserRoleEntity userRoleEntity;
}
