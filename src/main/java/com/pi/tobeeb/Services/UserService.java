package com.pi.tobeeb.Services;

import com.pi.tobeeb.Controllers.AuthController;
import com.pi.tobeeb.Entities.ResetToken;
import com.pi.tobeeb.Entities.User;
import com.pi.tobeeb.Payload.request.SmsNewPwd;
import com.pi.tobeeb.Payload.request.SmsRest;
import com.pi.tobeeb.Payload.response.MessageResponse;
import com.pi.tobeeb.Repositorys.ResetTokenRepository;
import com.pi.tobeeb.Repositorys.UserRepository;
import com.pi.tobeeb.Utils.CodeUtils;
import com.pi.tobeeb.Utils.SmsConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserService {
    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository repoUser;
    @Autowired
    private JavaMailSender userMailSender;
    @Autowired
    private ResetTokenRepository ResettokenRepo;
    @Autowired
    private SmsConfig smsService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired

    private EmailService emailService;

    @Autowired
    private VerificationTokenService verificationTokenService;
    public User activateUser(String token) {
        User user = repoUser.findByVerificationToken(token);
        if (user != null) {
            user.setIsverified(1);
            user.setVerificationToken(null);
            repoUser.save(user);
        }
        return user;
    }
    @Value("${spring.mail.username}")
    private String fromAddress;
    public void generatePasswordResetToken(String Email) {
        User user = repoUser.findByEmail(Email);
        if (user == null) {
            throw new UsernameNotFoundException("No user found with Email: " + Email);
        }

        String token = generateToken();

        ResetToken passwordResetToken = new ResetToken();
        passwordResetToken.setToken(token);
        passwordResetToken.setUser(user);
        passwordResetToken.setExpiryDate(LocalDateTime.now().plusHours(24));
        ResettokenRepo.save(passwordResetToken);
        String subject = "Password reset request";
        String text = "Please click the following link to reset your password: http://localhost:8075/forgot-password?token=" + token;
        emailService.sendEmail(user.getEmail(), subject, text);
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }
    public User findByEmail(String Email) {
        return repoUser.findByEmail(Email);
    }

    public User findByUserEmail(String UserEmail)
    {
        return this.repoUser.findByEmail(UserEmail);
    }
    public ResponseEntity<?> SendSMS (SmsRest userResetPasswordSMS) {
        String response;
        // Retrieve user using the entered phone number.
        User user = this.findByPhone(userResetPasswordSMS.getPhone());
        System.out.println("la variable User est " + user);
        System.out.println("la variable Phone est " + userResetPasswordSMS.getPhone());
        if (user != null) {
            String code = CodeUtils.getSmsCode();
            System.out.println("le code est" + code);
            this.smsService.SendSMS(userResetPasswordSMS.getPhone(),code);
            System.out.println("la variable User est" + user);
            user.setUserCode(code);
            repoUser.save(user);
            response = "done";
        } else {
            response ="error";
        }
        return ResponseEntity.ok(new MessageResponse(response));
    }
    public User findByPhone(String phone)
    {
        return this.repoUser.findByphonenumber(phone);
    }
    public ResponseEntity<?> resetSMS(SmsNewPwd userNewPasswordSMS) {
        String response;
        User user = this.findByPhone(userNewPasswordSMS.getPhone());
        if(user != null){
            if(user.getUserCode().equals(userNewPasswordSMS.getCode())){
                user.setPassword(passwordEncoder.encode(userNewPasswordSMS.getPassword()));
                user.setUserCode("expired");
                repoUser.save(user);
                response = "done";
            } else {
                response ="error";
            }
        } else {
            response ="error";
        }
        return ResponseEntity.ok(new MessageResponse(response));
    }

    public void delete(String userName){
        User u= repoUser.findByUsername(userName).orElse(null);
        u.getRole().clear();
        repoUser.delete(u);
    }
    public void update(User user){
        logger.info(user.getRoles().toString());

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repoUser.save(user);
    }


    public Boolean isValid(String username){
        User user = repoUser.findByUsername(username).get();
        if(user.getIsverified() == 1) {
            return true;
        }
            return false;

    }
}
