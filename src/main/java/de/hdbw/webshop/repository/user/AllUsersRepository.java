package de.hdbw.webshop.repository.user;

import de.hdbw.webshop.model.users.entity.AllUsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AllUsersRepository extends JpaRepository<AllUsersEntity, Long> {
    Optional<AllUsersEntity> findById(Long id);
}
