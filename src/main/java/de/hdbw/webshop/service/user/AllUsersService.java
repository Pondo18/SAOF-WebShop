package de.hdbw.webshop.service.user;

import de.hdbw.webshop.model.users.AllUsersEntity;
import org.springframework.stereotype.Service;

@Service
public class AllUsersService {
    public AllUsersEntity createNewAllUsersEntity () {
        return new AllUsersEntity();
    }
}
