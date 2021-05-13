package de.hdbw.webshop.model.users.entity;

import de.hdbw.webshop.model.users.User;
import de.hdbw.webshop.model.users.entity.AllUsersEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "unregistered_user")
@Data
@NoArgsConstructor
public class UnregisteredUserEntity implements User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;
    private String jsessionid;
    private LocalDate expireDate;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private AllUsersEntity allUsers;

    public UnregisteredUserEntity(String jsessionid, LocalDate expireDate, AllUsersEntity allUsers) {
        this.jsessionid = jsessionid;
        this.expireDate = expireDate;
        this.allUsers = allUsers;
    }

    public UnregisteredUserEntity(String jsessionid, AllUsersEntity allUsers) {
        this.jsessionid = jsessionid;
        this.allUsers = allUsers;
    }

    public UnregisteredUserEntity(String jsessionid) {
        this.jsessionid = jsessionid;
    }

    @Override
    public long getUserId() {
        return allUsers.getId();
    }
}
