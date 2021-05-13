package de.hdbw.webshop.repository.user;

import de.hdbw.webshop.model.users.entity.RegisteredUsersEntity;
import de.hdbw.webshop.model.users.entity.UserPasswordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPasswordRepository extends JpaRepository<UserPasswordEntity, Long> {
    UserPasswordEntity findByRegisteredUser(RegisteredUsersEntity registeredUserEntity);
}
