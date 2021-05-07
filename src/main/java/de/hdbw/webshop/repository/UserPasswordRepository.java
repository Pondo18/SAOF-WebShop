package de.hdbw.webshop.repository;

import de.hdbw.webshop.model.user.RegisteredUserEntity;
import de.hdbw.webshop.model.user.UserPasswordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserPasswordRepository extends JpaRepository<UserPasswordEntity, Long> {
    UserPasswordEntity findByRegisteredUser(RegisteredUserEntity registeredUserEntity);
}
