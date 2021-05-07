package de.hdbw.webshop.model.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "unregistered_user")
@Data
@NoArgsConstructor
public class UnregisteredUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;
    private String sessionId;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private AllUsersEntity allUsers;

}
