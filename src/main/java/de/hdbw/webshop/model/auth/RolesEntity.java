package de.hdbw.webshop.model.auth;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
public class RolesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;
    private String role;
    @OneToMany(mappedBy = "role")
    private Set<UserRoleEntity> userRoleEntity;

    public RolesEntity() {

    }

    public RolesEntity(Long id, String role) {
        this.id = id;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public Set<UserRoleEntity> getUserRoleEntity() {
        return userRoleEntity;
    }

    @Override
    public String toString() {
        return "RolesEntity{" +
                "id=" + id +
                ", role='" + role + '\'' +
                '}';
    }
}
