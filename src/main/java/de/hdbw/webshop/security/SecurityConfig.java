package de.hdbw.webshop.security;

import de.hdbw.webshop.listener.MySessionListener;
import de.hdbw.webshop.service.artwork.ShoppingCartService;
import de.hdbw.webshop.service.session.RedirectHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final MySessionListener mySessionListener;
    private final RedirectHelper redirectHelper;
    private final ShoppingCartService shoppingCartService;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService, MySessionListener mySessionListener, RedirectHelper redirectHelper, ShoppingCartService shoppingCartService) {
        this.userDetailsService = userDetailsService;
        this.mySessionListener = mySessionListener;
        this.redirectHelper = redirectHelper;
        this.shoppingCartService = shoppingCartService;
    }

    @Bean
    public BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public UserDetailsService userDetailsService() {
        return userDetailsService;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(getPasswordEncoder());
        return authProvider;
    }


    @Bean("authenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public ServletListenerRegistrationBean<MySessionListener> sessionListenerWithMetrics() {
        ServletListenerRegistrationBean<MySessionListener> listenerRegBean =
                new ServletListenerRegistrationBean<>();

        listenerRegBean.setListener(mySessionListener);
        return listenerRegBean;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .csrf().disable()
                .authorizeRequests().antMatchers("/artwork*").permitAll()
                .antMatchers("/**").permitAll()
                .antMatchers("/registration/user").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
//                .defaultSuccessUrl("/artworks")
                .defaultSuccessUrl("/artworks", true)
                .successHandler(new MyAuthenticationSuccessHandler(redirectHelper, shoppingCartService));
//                .and()
//                .logout().deleteCookies("JSESSIONID").logoutSuccessHandler(new CustomLogoutHandler());
    }
}
