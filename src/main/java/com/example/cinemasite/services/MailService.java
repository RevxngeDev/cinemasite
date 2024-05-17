package com.example.cinemasite.services;

public interface MailService {
    void sendEmailForConfirm(String email, String code);
}
