package de.hdbw.webshop.primary.repository;

import de.hdbw.webshop.primary.model.auth.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {
}
