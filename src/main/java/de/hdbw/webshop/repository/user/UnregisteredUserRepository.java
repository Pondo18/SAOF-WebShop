package de.hdbw.webshop.repository.user;

import de.hdbw.webshop.model.users.UnregisteredUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnregisteredUserRepository extends JpaRepository<UnregisteredUserEntity, Long> {
}
