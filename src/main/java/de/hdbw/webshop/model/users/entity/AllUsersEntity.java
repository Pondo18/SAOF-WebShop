package de.hdbw.webshop.model.users.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "all_users")
@Data
@NoArgsConstructor
public class AllUsersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @OneToOne(mappedBy = "allUsers")
    private RegisteredUsersEntity registeredUser;

    @OneToOne(mappedBy = "allUsers")
    private UnregisteredUserEntity unregisteredUser;
}
