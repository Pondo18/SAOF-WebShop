package de.hdbw.webshop.model.auth;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
public class RolesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;
    @Column(unique = true)
    private String role;
    @OneToMany(mappedBy = "role")
    private Set<UserRoleEntity> userRoleEntity;
}
