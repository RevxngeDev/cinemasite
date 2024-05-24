package com.example.cinemasite.controllers;

import com.example.cinemasite.models.State;
import com.example.cinemasite.models.User;
import com.example.cinemasite.repositores.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ConfirmController {

    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("/confirm/{code}")
    public ModelAndView confirmUser(@PathVariable("code") String code) {
        User user = usersRepository.findByConfirmCode(code);
        if (user != null) {
            user.setState(State.CONFIRMED);
            usersRepository.save(user);
            return new ModelAndView("confirmacion");
        } else {
            return new ModelAndView("error");
        }
    }
}
