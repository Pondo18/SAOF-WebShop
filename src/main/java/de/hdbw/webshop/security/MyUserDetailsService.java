package de.hdbw.webshop.security;

import de.hdbw.webshop.model.users.entity.ArtistEntity;
import de.hdbw.webshop.model.users.entity.RegisteredUsersEntity;
import de.hdbw.webshop.model.users.entity.UserPasswordEntity;
import de.hdbw.webshop.service.artist.ArtistService;
import de.hdbw.webshop.service.user.RegisteredUserService;
import de.hdbw.webshop.service.user.UserPasswordService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@CommonsLog
public class MyUserDetailsService implements UserDetailsService {

    private final RegisteredUserService registeredUserService;
    private final UserPasswordService userPasswordService;
    private final ArtistService artistService;

    @Autowired
    public MyUserDetailsService(RegisteredUserService registeredUserService, UserPasswordService userPasswordService, ArtistService artistService) {
        this.registeredUserService = registeredUserService;
        this.userPasswordService = userPasswordService;
        this.artistService = artistService;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            RegisteredUsersEntity registeredUserEntity = registeredUserService.findRegisteredUserEntityByEmail(email);
            ArtistEntity artistEntity = artistService.findArtistIfUserIsArtist(registeredUserEntity);
            UserPasswordEntity userPasswordEntity = userPasswordService.findUserPasswordEntity(registeredUserEntity);
            if (artistEntity!=null) {
                artistEntity.setRegisteredUserEntity(registeredUserEntity);
                log.info("Logging in artist with the email: " + email );
                return new MyUserDetails(artistEntity, userPasswordEntity);
            } else {
                log.info("Logging in user with the email: " + email );
                return new MyUserDetails(registeredUserEntity, userPasswordEntity);
            }
        } catch (UsernameNotFoundException notFoundException) {
            log.info("User login failed: There is no user with the email: " + email);
            throw new UsernameNotFoundException("There is no registered user with the email: " + email);
        }
    }
}
