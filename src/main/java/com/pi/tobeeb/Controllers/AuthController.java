package com.pi.tobeeb.Controllers;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import com.pi.tobeeb.Entities.*;
import com.pi.tobeeb.Jwt.JwtUtils;
import com.pi.tobeeb.Payload.request.*;
import com.pi.tobeeb.Payload.response.JwtResponse;
import com.pi.tobeeb.Payload.response.MessageResponse;
import com.pi.tobeeb.Repositorys.RoleRepository;
import com.pi.tobeeb.Repositorys.UserRepository;
import com.pi.tobeeb.Security.UserDetailsImp;
import com.pi.tobeeb.Services.EmailService;
import com.pi.tobeeb.Services.UserService;
import com.pi.tobeeb.Services.VerificationTokenService;
import com.pi.tobeeb.Utils.CodeUtils;
import com.pi.tobeeb.Entities.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private Logger logger = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    EmailService emailService;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    private VerificationTokenService verificationTokenService;


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) throws Exception {
        User usr = userRepository.findByUsername(loginRequest.getUsername()).get();
       String message="" ;
        if(usr !=null) {
            if (!usr.isAccountNonLocked() && usr.getFailedAttempt() >= userService.MAX_FAILED_ATTEMPTS) {
                message="Your account has been locked due to 3 failed attempts."
                        + " It will be unlocked after 24 hours";
                logger.error(message);

                throw new LockedException("Your account has been locked due to 3 failed attempts."
                        + " It will be unlocked after 24 hours.");

            }
            logger.info("iciii");
            if (!usr.isAccountNonLocked()) {
                if (userService.unlockWhenTimeExpired(usr)) {
                    message="Your account has been unlocked. Please try to login again.";
                    logger.error(message);

                    throw new LockedException("Your account has been unlocked. Please try to login again.");
                }


            }
                try {
                    Authentication authentication = authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
                    if (userService.isValid(loginRequest.getUsername()) == true) {
                        SecurityContextHolder.getContext().setAuthentication(authentication);

                        String jwt = jwtUtils.generateJwtToken(authentication);

                        UserDetailsImp userDetails = (UserDetailsImp) authentication.getPrincipal();
                        List<String> roles = userDetails.getAuthorities().stream()
                                .map(item -> item.getAuthority())
                                .collect(Collectors.toList());
                        if (usr.getFailedAttempt() > 0) {
                            userService.resetFailedAttempts(usr.getUsername());
                        }

                        return ResponseEntity.ok(new JwtResponse(jwt,
                                userDetails.getUser().getIdUser(), userDetails.getUsername(),
                                userDetails.getUser().getEmail(), roles));
                    }
                    return ResponseEntity
                            .badRequest()
                            .body(new MessageResponse("Account is  not verified!"));
                } catch (BadCredentialsException e) {
                    userService.increaseFailedAttempts(usr);
                    if (usr.getFailedAttempt() >= userService.MAX_FAILED_ATTEMPTS) {
                        userService.lock(usr);
                        message="Your account has been locked due to 3 failed attempts.\"\n" +
                                "                                + \" It will be unlocked after 24 hours.";
                        logger.error(message);

                        throw new LockedException("Your account has been locked due to 3 failed attempts."
                                + " It will be unlocked after 24 hours.");

                    }
                    message="Bad credentials";
                    logger.error(message);

                    throw new Exception("INVALID_CREDENTIALS", e);

                }

            }

        return ResponseEntity
                .badRequest()
                .body(new MessageResponse(message));
    }


    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SingupRequest signUpRequest) {
        logger.error("iiiiiicccciiiiiiiiii");
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        logger.error(strRoles.toString());

        Set<Role> roles = new HashSet<>();
        logger.error("iiiiiicccciiiiiiiiii");

        logger.error(String.valueOf(strRoles));

        if (strRoles == null) {

            Role userRole = roleRepository.findByName(ERole.ROLE_PATIENT)

                    .orElseThrow(() -> new RuntimeException("Error: Role5 is not found."));

            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "patient":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_PATIENT)
                                .orElseThrow(() -> new RuntimeException("Error: Role3 is not found."));
                        roles.add(adminRole);

                        break;
                    case "doctor":
                        Role modRole = roleRepository.findByName(ERole.ROLE_DOCTOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role2 is not found."));
                        roles.add(modRole);

                        break;
                    default:

                        Role userRole = roleRepository.findByName(ERole.ROLE_PHARMACY)
                                .orElseThrow(() -> new RuntimeException("Error: Role1 is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);
        emailService.sendVerificationEmail(user);
        UserVerificationToken verificationToken = verificationTokenService.createVerificationToken(user); // création du jeton de vérification
        verificationTokenService.saveVerificationToken(verificationToken);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PutMapping("/activate/{verificationToken}")
    public ResponseEntity activateAccount(@PathVariable String verificationToken) {
        logger.error("tokeen");
        logger.error(verificationToken);


        User user = userService.activateUser(verificationToken);
        if (user != null) {
            String to = user.getEmail();
            String subject = "Account Created";
            String text = "Your account has been created successfully.";
            emailService.sendEmail(to, subject, text);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/ResetPasswordMail")
    public ResponseEntity<?>  resetPasswordEmail(@RequestBody ResetPassword resetPassword){
        String response;
        User user = this.userService.findByUserEmail(resetPassword.getEmail());
        if(user != null){
            String code = CodeUtils.getCode();
            System.out.println("le code est" + code);
            UserMail mail = new UserMail(resetPassword.getEmail(),code);
            System.out.println("le mail est" + resetPassword.getEmail());
            System.out.println("la variable mail est" + mail);
            emailService.sendCodeByMail(mail);
            System.out.println("la variable User est" + user);
            user.setUserCode(code);
            userRepository.save(user);
            response = "done";
        } else {
            response ="error";
        }
        return ResponseEntity.ok(new MessageResponse(response));
    }

    @PostMapping("/resetPassword")
    public  ResponseEntity<?>  resetPassword(@RequestBody ResetNewPassword newPassword){
        String response;

        User user = this.userService.findByUserEmail(newPassword.getEmail());
        if(user != null){
            if(user.getUserCode().equals(newPassword.getCode())){
                user.setPassword(passwordEncoder.encode(newPassword.getPassword()));
                userRepository.save(user);
                response = "done";
            } else {
                response ="error";
            }
        } else {
            response ="error";
        }
        return ResponseEntity.ok(new MessageResponse(response));
    }

    @PostMapping("/restPwdSms")
    public ResponseEntity<?>  SendSMS (@RequestBody SmsRest userResetPasswordSMS) {
        return userService.SendSMS(userResetPasswordSMS);
    }

    @PostMapping("/ChangePwdSms")
    public ResponseEntity<?> resetPasswordSMS (@RequestBody SmsNewPwd newPassword) {
        return userService.resetSMS(newPassword);
    }
    @DeleteMapping ({"/delete/{userName}"})
    @PreAuthorize("hasRole('ROLE_ADMIN')")

    public void delete(@PathVariable String userName){
        userService.delete(userName);
    }

    @PutMapping(value="/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) throws IOException {

        User updatedUser = userService.update(id,user);

        return ResponseEntity.ok(updatedUser);
    }



}