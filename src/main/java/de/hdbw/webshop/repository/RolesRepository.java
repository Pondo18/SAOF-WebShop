package de.hdbw.webshop.repository;

import de.hdbw.webshop.model.auth.RolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RolesRepository extends JpaRepository<RolesEntity, Long> {
}
