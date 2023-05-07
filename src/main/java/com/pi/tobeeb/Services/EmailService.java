package com.pi.tobeeb.Services;

import java.io.Serializable;

import com.pi.tobeeb.Entities.User;
import com.pi.tobeeb.Entities.UserMail;
import com.pi.tobeeb.Repositorys.IUserEmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.MimeMessageHelper;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import java.util.Random;
@Service
public class EmailService implements IUserEmailRepository {


    @Autowired
    private JavaMailSender userMailSender;
    @Autowired
    private VerificationTokenService verificationTokenService;

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        userMailSender.send(message);
    }
    public void sendVerificationEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        user.setVerificationToken(verificationTokenService.generateVerificationToken());
        message.setSubject("Vérification du compte");
        message.setText("Bonjour " + user.getUsername() + ",\n\n" +

                "Veuillez cliquerr sur le lien ci-dessouus pour activer votre compte :\n\n" +
                "http://localhost:4200/activate?token=" + user.getVerificationToken());

        userMailSender.send(message);
    }
    @Override
    public void sendCodeByMail(UserMail mail) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("tobeeb2023@gmail.com");
        simpleMailMessage.setTo(mail.getTo());

        simpleMailMessage.setSubject("Code Active!!");
        simpleMailMessage.setText("Hello This code will let you change your forget password");
        simpleMailMessage.setText(mail.getCode());
        userMailSender.send(simpleMailMessage);
    }

    public void sendConfirmationEmail(String recipientEmail) throws MessagingException {
        MimeMessage message = userMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(recipientEmail);
        helper.setSubject("Confirmation de rendez-vous");
        helper.setText("Votre rendez-vous  a été ajouté avec succès.");
        userMailSender.send(message);
    }

}