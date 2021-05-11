package de.hdbw.webshop.repository.user;

import de.hdbw.webshop.model.users.AllUsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllUsersRepository extends JpaRepository<AllUsersEntity, Long> {
    AllUsersEntity findById(long id);
}
