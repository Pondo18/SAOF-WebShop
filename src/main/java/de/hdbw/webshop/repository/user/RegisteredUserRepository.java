package de.hdbw.webshop.repository.user;

import de.hdbw.webshop.model.users.entity.RegisteredUsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegisteredUserRepository extends JpaRepository<RegisteredUsersEntity, Long> {
    Optional<RegisteredUsersEntity> findByEmail(String email);
}
