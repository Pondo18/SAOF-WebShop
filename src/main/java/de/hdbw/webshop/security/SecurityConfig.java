package de.hdbw.webshop.security;

import de.hdbw.webshop.model.user.UserPasswordEntity;
import de.hdbw.webshop.repository.ArtistRepository;
import de.hdbw.webshop.repository.RegisteredUserRepository;
import de.hdbw.webshop.repository.UserPasswordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;
    private final RegisteredUserRepository registeredUserRepository;
    private final ArtistRepository artistRepository;
    private final UserPasswordRepository userPasswordRepository;

    @Autowired
    public SecurityConfig(DataSource dataSource, RegisteredUserRepository registeredUserRepository, ArtistRepository artistRepository, UserPasswordRepository userPasswordRepository) {
        this.dataSource = dataSource;
        this.registeredUserRepository = registeredUserRepository;
        this.artistRepository = artistRepository;
        this.userPasswordRepository = userPasswordRepository;
    }

//    @Autowired
//    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .jdbcAuthentication().dataSource(dataSource)
//                .usersByUsernameQuery("select email, password, enabled from users where email=?")
//                .authoritiesByUsernameQuery("with table1 as (\n" +
//                        "    SELECT users.email, user_role.role_id\n" +
//                        "    FROM user_role\n" +
//                        "             left join users ON user_role.user_id = users.id WHERE email =?\n " +
//                        ")\n" +
//                        "SELECT email, role\n" +
//                        "from table1 left join roles on table1.role_id = roles.id");
//
//    }

    @Bean
    public BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new MyUserDetailsService(artistRepository, registeredUserRepository, userPasswordRepository);
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(getPasswordEncoder());

        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests().antMatchers("/artworks").permitAll()
                .antMatchers("/**").permitAll()
                .antMatchers("/registration/user").permitAll()
                .anyRequest().authenticated() 
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/artworks");
    }


}
