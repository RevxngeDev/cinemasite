package com.example.cinemasite.controllers;

import com.example.cinemasite.models.User;
import com.example.cinemasite.repositores.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ResetPasswordController {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/reset-password")
    public String showResetPasswordPage(@RequestParam("token") String token, Model model) {
        User user = usersRepository.findByConfirmCode(token);
        if (user == null) {
            return "redirect:/error?message=invalidToken";
        }
        model.addAttribute("token", token);
        return "resetPassword";
    }

    @GetMapping("/resetPasswordSuccess")
    public String resetPasswordSuccess(){
        return "resetPasswordSuccess";
    }

    @PostMapping("/reset-password")
    public String handleResetPassword(@RequestParam("token") String token,
                                      @RequestParam("password") String password,
                                      @RequestParam("confirmPassword") String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            return "redirect:/reset-password?token=" + token + "&error=passwordMismatch";
        }

        User user = usersRepository.findByConfirmCode(token);
        if (user == null) {
            return "redirect:/reset-password?token=" + token + "&error=invalidToken";
        }

        user.setPassword(passwordEncoder.encode(password));
        usersRepository.save(user);

        return "redirect:/resetPasswordSuccess";
    }
}




