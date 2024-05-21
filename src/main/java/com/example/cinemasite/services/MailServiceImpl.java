package com.example.cinemasite.services;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassRelativeResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.SpringTemplateLoader;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@Component
public class MailServiceImpl implements MailService{

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String mailFrom;

    private final Template confirmMailTemplate;
    private final Template reservationMailTemplate;
    private final Template resetPasswordMailTemplate;

    public MailServiceImpl() {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_30);
        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateLoader(
                new SpringTemplateLoader(new ClassRelativeResourceLoader(this.getClass()), "/"));
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        try {
            this.confirmMailTemplate = configuration.getTemplate("templates/confirm_mail.ftlh");
            this.reservationMailTemplate = configuration.getTemplate("templates/reservation_mail.ftlh");
            this.resetPasswordMailTemplate = configuration.getTemplate("templates/reset_password_mail.ftlh");
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }


    @Override
    public void sendEmailForConfirm(String email, String code) {
        String mailText = getEmailText(confirmMailTemplate, "confirm_code", code);
        MimeMessagePreparator messagePreparator = getEmail(email, mailText, "Регистрация");
        javaMailSender.send(messagePreparator);
    }

    @Override
    public void sendReservationEmail(String email, String filmName, List<Long> seatIds) {
        String mailText = getReservationEmailText(filmName, seatIds);
        MimeMessagePreparator messagePreparator = getEmail(email, mailText, "Confirmación de Reserva");
        javaMailSender.send(messagePreparator);
    }
    @Override
    public void sendResetPasswordEmail(String email, String link) {
        String mailText = getEmailText(resetPasswordMailTemplate, "reset_link", link);
        MimeMessagePreparator messagePreparator = getEmail(email, mailText, "Restablecimiento de Contraseña");
        javaMailSender.send(messagePreparator);
    }

    private MimeMessagePreparator getEmail(String email, String mailText, String subject) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(mailFrom);
            messageHelper.setTo(email);
            messageHelper.setSubject(subject);
            messageHelper.setText(mailText, true);
        };
    }

    private String getEmailText(Template template, String key, String value) {
        Map<String, String> attributes = new HashMap<>();
        attributes.put(key, value);
        StringWriter writer = new StringWriter();
        try {
            template.process(attributes, writer);
        } catch (TemplateException | IOException e) {
            throw new IllegalStateException(e);
        }
        return writer.toString();
    }
    private String getReservationEmailText(String filmName, List<Long> seatIds) {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("filmName", filmName);
        attributes.put("seatIds", seatIds);
        StringWriter writer = new StringWriter();
        try {
            reservationMailTemplate.process(attributes, writer);
        } catch (TemplateException | IOException e) {
            throw new IllegalStateException(e);
        }
        return writer.toString();
    }
}