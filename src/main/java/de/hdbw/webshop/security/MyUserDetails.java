package de.hdbw.webshop.security;

import de.hdbw.webshop.model.users.ArtistEntity;
import de.hdbw.webshop.model.users.User;
import de.hdbw.webshop.model.users.UserPasswordEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static de.hdbw.webshop.model.users.Roles.*;

public class MyUserDetails implements UserDetails {

    private User user;
    private UserPasswordEntity userPasswordEntity;

    public MyUserDetails(User user, UserPasswordEntity userPasswordEntity) {
        this.user = user;
        this.userPasswordEntity = userPasswordEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + REGISTERED_USER.name()));


        if (user.getClass().equals(ArtistEntity.class)) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + ARTIST.name()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return userPasswordEntity.getPasswordHash();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
}
