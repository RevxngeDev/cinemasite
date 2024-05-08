package com.example.cinemasite.services;

import com.example.cinemasite.dto.UserForm;
import com.example.cinemasite.models.Role;
import com.example.cinemasite.models.User;
import com.example.cinemasite.repositores.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class SignUpServiceImpl implements SignUpService{

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void addUser(UserForm form) {
        User user = User.builder()
                .email(form.getEmail())
                .password(passwordEncoder.encode(form.getPassword()))
                .firstName(form.getFirstname())
                .lastName(form.getLastname())
                .phone(form.getPhone())
                .confirmed("CONFIRMED")
                .role(Role.USER)
                .build();
        usersRepository.save(user);
    }
}
