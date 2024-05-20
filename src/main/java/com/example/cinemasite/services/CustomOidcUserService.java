package com.example.cinemasite.services;

import com.example.cinemasite.models.Role;
import com.example.cinemasite.models.State;
import com.example.cinemasite.models.User;
import com.example.cinemasite.repositores.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Component
public class CustomOidcUserService extends OidcUserService {

    private final UsersRepository userRepository;

    @Autowired
    public CustomOidcUserService(UsersRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);

        // Extraer los atributos del usuario de Google
        String email = oidcUser.getEmail();
        String firstName = oidcUser.getGivenName();
        String lastName = oidcUser.getFamilyName();

        // Comprobar si el usuario ya existe en la base de datos
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isEmpty()) {
            // Si no existe, crear un nuevo usuario y guardarlo en la base de datos
            User newUser = User.builder()
                    .email(email)
                    .firstName(firstName)
                    .lastName(lastName)
                    .password(passwordEncoder.encode("camilo"))
                    .role(Role.USER)
                    .confirmCode(UUID.randomUUID().toString())
                    .state(State.CONFIRMED)
                    .phone("842348184")
                    .createdAt(LocalDateTime.now())
                    // Aquí puedes establecer otros atributos del usuario según sea necesario
                    .build();
            userRepository.save(newUser);
        }

        return oidcUser;
    }
}

