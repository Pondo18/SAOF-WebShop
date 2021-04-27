package de.hdbw.webshop.primary.repository;

import de.hdbw.webshop.primary.model.auth.RolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RolesRepository extends JpaRepository<RolesEntity, Long> {
}
