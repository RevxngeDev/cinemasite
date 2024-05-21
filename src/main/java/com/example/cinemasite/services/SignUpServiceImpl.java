package com.example.cinemasite.services;

import com.example.cinemasite.dto.UserForm;
import com.example.cinemasite.models.Role;
import com.example.cinemasite.models.State;
import com.example.cinemasite.models.User;
import com.example.cinemasite.repositores.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;


@Component
public class SignUpServiceImpl implements SignUpService{

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailService mailService;

    @Autowired
    private SmsService smsService;

    @Override
    public void addUser(UserForm form) {
        User user = User.builder()
                .email(form.getEmail())
                .password(passwordEncoder.encode(form.getPassword()))
                .firstName(form.getFirstname())
                .lastName(form.getLastname())
                .phone(form.getPhone())
                .state(State.NOT_CONFIRMED)
                .role(Role.USER)
                .confirmCode(UUID.randomUUID().toString())
                .createdAt(LocalDateTime.now())
                .build();
        usersRepository.save(user);
        smsService.sendSms(form.getPhone(), "Welcome to Cinemasite!");
        mailService.sendEmailForConfirm(user.getEmail(), user.getConfirmCode());
    }
}
