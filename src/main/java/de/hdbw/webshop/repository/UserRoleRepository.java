package de.hdbw.webshop.repository;

import de.hdbw.webshop.model.auth.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {
}
