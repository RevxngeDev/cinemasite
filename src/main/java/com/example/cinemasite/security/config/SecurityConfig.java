package com.example.cinemasite.security.config;

import com.example.cinemasite.repositores.UsersRepository;
import com.example.cinemasite.services.CustomOidcUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    @Qualifier("customUserDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    private CustomOidcUserService customOidcUserService;

    @Autowired
    private AuthenticationSuccessHandler customOAuth2SuccessHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/signUp", "/users", "/dashboard", "/films").permitAll()
                .antMatchers("/profile").authenticated()
                .and()
                .formLogin()
                .loginPage("/signIn")
                .usernameParameter("email")
                .defaultSuccessUrl("/home")
                .failureUrl("/signIn?error")
                .permitAll()
                .and()
                .oauth2Login()
                .loginPage("/signIn")
                .defaultSuccessUrl("/home", true)
                .userInfoEndpoint()
                .oidcUserService(customOidcUserService)
                .and()
                .successHandler(customOAuth2SuccessHandler);
    }

    @Bean
    public OidcUserService oidcUserService(UsersRepository usersRepository) {
        return new CustomOidcUserService(usersRepository);
    }

}
