package de.hdbw.webshop.repository.user;

import de.hdbw.webshop.model.users.entity.UnregisteredUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UnregisteredUserRepository extends JpaRepository<UnregisteredUserEntity, Long> {
    Optional<UnregisteredUserEntity> findByJsessionid(String jsessionid);
}
