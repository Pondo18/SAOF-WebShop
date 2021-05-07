package de.hdbw.webshop.model.user;

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
    private RegisteredUserEntity registeredUser;

    @OneToOne(mappedBy = "allUsers")
    private UnregisteredUserEntity unregisteredUser;
}
