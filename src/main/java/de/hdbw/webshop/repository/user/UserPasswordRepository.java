package de.hdbw.webshop.repository.user;

import de.hdbw.webshop.model.users.RegisteredUserEntity;
import de.hdbw.webshop.model.users.UserPasswordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPasswordRepository extends JpaRepository<UserPasswordEntity, Long> {
    UserPasswordEntity findByRegisteredUser(RegisteredUserEntity registeredUserEntity);
}
