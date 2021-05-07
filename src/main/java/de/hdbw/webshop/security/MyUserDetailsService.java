package de.hdbw.webshop.security;

import de.hdbw.webshop.model.user.ArtistEntity;
import de.hdbw.webshop.model.user.RegisteredUserEntity;
import de.hdbw.webshop.model.user.UserPasswordEntity;
import de.hdbw.webshop.repository.ArtistRepository;
import de.hdbw.webshop.repository.RegisteredUserRepository;
import de.hdbw.webshop.repository.UserPasswordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final ArtistRepository artistRepository;
    private final RegisteredUserRepository registeredUserRepository;
    private final UserPasswordRepository userPasswordRepository;

    @Autowired
    public MyUserDetailsService(ArtistRepository artistRepository, RegisteredUserRepository registeredUserRepository, UserPasswordRepository userPasswordRepository) {
        this.artistRepository = artistRepository;
        this.registeredUserRepository = registeredUserRepository;
        this.userPasswordRepository = userPasswordRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        RegisteredUserEntity registeredUserEntity = findRegisteredUserEntity(email);
        if (registeredUserEntity!= null) {
            ArtistEntity artistEntity = findArtistIfUserIsArtist(registeredUserEntity);
            UserPasswordEntity userPasswordEntity = findUserPasswordEntity(registeredUserEntity);
            if (artistEntity!=null) {
                artistEntity.setRegisteredUserEntity(registeredUserEntity);
                return new MyUserDetails(artistEntity, userPasswordEntity);
            } else {
                return new MyUserDetails(registeredUserEntity, userPasswordEntity);
            }
        } else {
            throw new UsernameNotFoundException("There is no registered user with the email: " + email);
        }
    }

    public ArtistEntity findArtistIfUserIsArtist(RegisteredUserEntity registeredUserEntity) {
        return artistRepository.findByRegisteredUserEntity(registeredUserEntity).orElse(null);
    }

    public RegisteredUserEntity findRegisteredUserEntity (String email) {
        return registeredUserRepository.findByEmail(email).orElse(null);
    }

    public UserPasswordEntity findUserPasswordEntity (RegisteredUserEntity registeredUserEntity) {
        return userPasswordRepository.findByRegisteredUser(registeredUserEntity);
    }
}
