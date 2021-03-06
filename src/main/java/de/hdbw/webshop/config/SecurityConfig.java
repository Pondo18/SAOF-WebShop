package de.hdbw.webshop.config;

import de.hdbw.webshop.handler.MyAccessDeniedHandler;
import de.hdbw.webshop.listener.MySessionListener;
import de.hdbw.webshop.handler.MyAuthenticationSuccessHandler;
import de.hdbw.webshop.model.users.Roles;
import de.hdbw.webshop.service.user.ShoppingCartService;
import de.hdbw.webshop.service.session.RedirectHelper;
import de.hdbw.webshop.util.string.UrlUtil;
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
import org.springframework.security.web.access.AccessDeniedHandler;

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
    private final UrlUtil urlUtil;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService, MySessionListener mySessionListener, RedirectHelper redirectHelper, ShoppingCartService shoppingCartService, UrlUtil urlUtil) {
        this.userDetailsService = userDetailsService;
        this.mySessionListener = mySessionListener;
        this.redirectHelper = redirectHelper;
        this.shoppingCartService = shoppingCartService;
        this.urlUtil = urlUtil;
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

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new MyAccessDeniedHandler();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authorizeRequests().antMatchers("/artwork*").permitAll()
                .antMatchers("/**").permitAll()
                .antMatchers("/registration/user").permitAll()
                .anyRequest().authenticated()
                .and()
//                .exceptionHandling().accessDeniedHandler(accessDeniedHandler())
//                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/")
//                .defaultSuccessUrl("/artworks", true)
                .successHandler(new MyAuthenticationSuccessHandler(redirectHelper, shoppingCartService, urlUtil))
                .and()
                .logout().logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");
    }
}
