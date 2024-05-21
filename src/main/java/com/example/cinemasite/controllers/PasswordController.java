package com.example.cinemasite.controllers;

import com.example.cinemasite.models.User;
import com.example.cinemasite.repositores.UsersRepository;
import com.example.cinemasite.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;
import java.util.UUID;

@Controller
public class PasswordController {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private MailService mailService;

    @GetMapping("/forgot-password")
    public String showForgotPasswordPage() {
        return "forgotPassword";
    }

    @GetMapping("/forgotPasswordSuccess")
    public String forgotPasswordSuccess(){
        return "forgotPasswordSuccess";
    }

    @PostMapping("/forgot-password")
    public String handleForgotPassword(@RequestParam("email") String email) {
        Optional<User> userOptional = usersRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String resetToken = UUID.randomUUID().toString();
            user.setConfirmCode(resetToken);
            usersRepository.save(user);

            String resetLink = "http://localhost:8080/reset-password?token=" + resetToken;
            mailService.sendResetPasswordEmail(email, resetLink);
        }
        return "redirect:/forgotPasswordSuccess";
    }
}

