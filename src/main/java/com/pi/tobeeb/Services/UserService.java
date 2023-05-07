package com.pi.tobeeb.Services;



import com.pi.tobeeb.Controllers.AuthController;
import com.pi.tobeeb.Entities.ERole;
import com.pi.tobeeb.Entities.ResetToken;
import com.pi.tobeeb.Entities.Role;
import com.pi.tobeeb.Entities.User;
import com.pi.tobeeb.Payload.request.ChangePasswordRequest;
import com.pi.tobeeb.Payload.request.SmsNewPwd;
import com.pi.tobeeb.Payload.request.SmsRest;
import com.pi.tobeeb.Payload.response.MessageResponse;
import com.pi.tobeeb.Repositorys.ResetTokenRepository;
import com.pi.tobeeb.Repositorys.UserRepository;
import com.pi.tobeeb.Utils.CodeUtils;
import com.pi.tobeeb.Utils.SmsConfig;
import com.pi.tobeeb.Repositorys.RoleRepository;
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

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class UserService {
    private Logger logger = LoggerFactory.getLogger(UserService.class);
    public static final int MAX_FAILED_ATTEMPTS = 3;

    private static final long LOCK_TIME_DURATION = 24 * 60 * 60 * 1000; // 24 hours
    @Autowired
    private UserRepository repoUser;
    @Autowired
    private JavaMailSender userMailSender;
    @Autowired
    private RoleRepository roleDao;

    @Autowired
    private ResetTokenRepository ResettokenRepo;
    @Autowired
    private SmsConfig smsService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired

    private EmailService emailService;


    public void initRoleAndUser() {

        Role role1 = new Role();
        role1.setName(ERole.ROLE_PATIENT);
        roleDao.save(role1);

        Role role2 = new Role();
        role2.setName(ERole.ROLE_DOCTOR);
        roleDao.save(role2);

        Role role3 = new Role();
        role3.setName(ERole.ROLE_PHARMACY);
        roleDao.save(role3);

        Role role4 = new Role();
        role4.setName(ERole.ROLE_ADMIN);
        roleDao.save(role4);
    }

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

    public void delete(Long id){
        User u= repoUser.findByIdUser(id);
        u.getRole().clear();
        repoUser.delete(u);
    }



    public User update(Long id, User user) throws IOException {
        User user2 = repoUser.findByIdUser(id);
        user2.setEmail(user.getEmail());
        user2.setImageProfile(user.getImageProfile());
        user2.setPhonenumber(user.getPhonenumber());
        user2.setAge(user.getAge());
        user2.setBloodType(user.getBloodType());
        user2.setFirstName(user.getFirstName());
        user2.setLastName(user.getLastName());
        user2.setCertificate(user.getCertificate());
        user2.setHeight(user.getHeight());
        user2.setWeight(user.getWeight());
        user2.setHourForWorkingEnd(user.getHourForWorkingEnd());
        user2.setHourForWorkingStart(user.getHourForWorkingStart());
        user2.setCity(user.getCity());
        user2.setEducation(user.getEducation());
        user2.setSpeciality(user.getSpeciality());
        user2.setGender(user.getGender());
        user2.setPostCode(user.getPostCode());
        return repoUser.save(user2);
    }

    public Boolean isValid(String username){
        User user = repoUser.findByUsername(username).get();
        if(user.getIsverified() == 1) {
            return true;
        }
            return false;

    }

    //login attempts
    public void increaseFailedAttempts(User user) {
        int newFailAttempts = user.getFailedAttempt() + 1;
        repoUser.updateFailedAttempts(newFailAttempts, user.getUsername());
    }

    public void resetFailedAttempts(String username) {
        repoUser.updateFailedAttempts(0, username);
    }

    public void lock(User user) {
        user.setAccountNonLocked(false);
        user.setLockTime(new Date());

        repoUser.save(user);
    }

    public boolean unlockWhenTimeExpired(User user) {
        long lockTimeInMillis = user.getLockTime().getTime();
        long currentTimeInMillis = System.currentTimeMillis();

        if (lockTimeInMillis + LOCK_TIME_DURATION < currentTimeInMillis) {
            logger.error("hounii");
            user.setAccountNonLocked(true);
            user.setLockTime(null);
            user.setFailedAttempt(0);

            repoUser.save(user);

            return true;
        }

        return false;

}
public List <User> GetByRole(ERole role){
        return repoUser.findByRoleName(role);
}


    public Integer changePassword(Long id, ChangePasswordRequest password) {
        User user = repoUser.findByIdUser(id);
        if (user == null) {
           return 404;
        }
        logger.info(password.getNewpassword());
        if(passwordEncoder.matches(password.getOldpassword(), user.getPassword())){
            user.setPassword(passwordEncoder.encode(password.getNewpassword()));
            repoUser.save(user);
            return 200;
        }
        else {
            return 400;
        }
    }

    public List < User > findAll() {
        return repoUser.findAll();
    }

}
