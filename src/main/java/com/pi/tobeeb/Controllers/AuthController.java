package com.pi.tobeeb.Controllers;


import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
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

import com.pi.tobeeb.Payload.response.ResponseType;
import com.pi.tobeeb.Repositorys.RoleRepository;
import com.pi.tobeeb.Repositorys.UserRepository;
import com.pi.tobeeb.Security.UserDetailsImp;
import com.pi.tobeeb.Services.EmailService;
import com.pi.tobeeb.Services.UserService;
import com.pi.tobeeb.Services.VerificationTokenService;
import com.pi.tobeeb.Utils.CodeUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
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
    public JwtResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest) throws Exception {
        User usr = userRepository.findByUsername(loginRequest.getUsername()).get();
        if(usr !=null) {
            if (!usr.isAccountNonLocked()) {
                if (userService.unlockWhenTimeExpired(usr)) {
                    return new  JwtResponse(320);
                }
            if (!usr.isAccountNonLocked() && usr.getFailedAttempt() >= userService.MAX_FAILED_ATTEMPTS) {

                return new  JwtResponse(120);

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

                        return new JwtResponse(jwt,
                                userDetails.getUser().getIdUser(), userDetails.getUsername(),
                                userDetails.getUser().getEmail(),roles,userDetails.getUser().getImageProfile(),userDetails.getUser().getPhonenumber(),
                                userDetails.getUser().getSpeciality(),userDetails.getUser().getGender(),userDetails.getUser().getHeight(),userDetails.getUser().getWeight(),
                                userDetails.getUser().getBloodType(), userDetails.getUser().getAge(),userDetails.getUser().getEducation(),userDetails.getUser().getCertificate(),userDetails.getUser().getFirstName(),userDetails.getUser().getLastName(),
                                userDetails.getUser().getHourForWorkingStart(),userDetails.getUser().getHourForWorkingEnd(),userDetails.getUser().getCity(),userDetails.getUser().getPostCode()
                        );

                    }
                    return new JwtResponse(230);
                } catch (BadCredentialsException e) {
                    userService.increaseFailedAttempts(usr);
                    if (usr.getFailedAttempt() >= userService.MAX_FAILED_ATTEMPTS) {
                        userService.lock(usr);

                        return new  JwtResponse(120);

                    }


                    return new JwtResponse(400);
                }

            }

        return new JwtResponse(404);
    }


    @PostMapping("/signup")
    public ResponseType registerUser(@Valid @RequestBody SingupRequest signUpRequest) {
        logger.error("iiiiiicccciiiiiiiiii");
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseType(400);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseType(404);

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


        /*Set<ERole> roles =  signUpRequest.getRole();
        Set<Role> valid_roles = new HashSet<Role>(){
        };
        roles.forEach(role -> {
            Role userRole = roleRepository.findByName(role).orElseThrow(() -> new RuntimeException("Error: Role invalid."));
            valid_roles.add(userRole);
        });
        user.setRoles(valid_roles);*/
        userRepository.save(user);
        //emailService.sendVerificationEmail(user);
        UserVerificationToken verificationToken = verificationTokenService.createVerificationToken(user); // création du jeton de vérification
        verificationTokenService.saveVerificationToken(verificationToken);
        System.out.println(verificationToken.toString());

        return new ResponseType(200);

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

    @DeleteMapping ({"/delete/{id}"})
    public void delete(@PathVariable Long id){
        userService.delete(id);
    }

    @PutMapping(value="/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) throws IOException {

        User updatedUser = userService.update(id,user);

        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/changepassword/{id}")
    public ResponseType changePassword(@PathVariable Long id, @RequestBody ChangePasswordRequest password) {
       Integer code = userService.changePassword(id, password);
        return new ResponseType(code);
    }

    @GetMapping ("findbymail/{email}")
    public User UserExist(@PathVariable String email) {
        return userRepository.findByEmail(email);
    }

    @GetMapping ("getAll")
    public Iterable<User>getAllUsers() {
        return userService.findAll();
    }


    @GetMapping ("getDoctor/{role}")
    public Iterable<User>GetDoctor(@PathVariable ERole role) {
        return userRepository.findByRoleName(role);
    }
    @GetMapping ("getDoctorById/{id}")
    public User GetDoctorById(@PathVariable Long id) {
        return userRepository.findByIdUser( id);
    }

}
