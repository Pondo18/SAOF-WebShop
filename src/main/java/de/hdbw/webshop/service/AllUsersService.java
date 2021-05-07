package de.hdbw.webshop.service;

import de.hdbw.webshop.model.users.AllUsersEntity;
import org.springframework.stereotype.Service;

@Service
public class AllUsersService {
    public AllUsersEntity createNewAllUsersEntity () {
        return new AllUsersEntity();
    }
}
