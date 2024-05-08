package com.example.cinemasite.controllers;

import com.example.cinemasite.dto.UserForm;
import com.example.cinemasite.services.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SignUpController {

    @Autowired
    private SignUpService signUpService;

    @GetMapping("/signUp")
    public String getSignUpPage(){
        return "signup";
    }

    @PostMapping("/signUp")
    public String signUp(UserForm form){
        System.out.println(form.toString());
        signUpService.addUser(form);
        return "redirect:/signIn";
    }
}
