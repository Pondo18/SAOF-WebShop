package de.hdbw.webshop.repository;

import de.hdbw.webshop.model.users.RegisteredUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegisteredUserRepository extends JpaRepository<RegisteredUserEntity, Long> {
    Optional<RegisteredUserEntity> findByEmail(String email);

}
