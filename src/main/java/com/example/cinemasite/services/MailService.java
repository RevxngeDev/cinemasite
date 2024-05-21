package com.example.cinemasite.services;

import java.util.List;

public interface MailService {
    void sendEmailForConfirm(String email, String code);
    void sendReservationEmail(String email, String filmName, List<Long> seatIds);
    void sendResetPasswordEmail(String email, String link);// Nuevo método
}

