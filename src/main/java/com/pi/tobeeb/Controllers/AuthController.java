package com.pi.tobeeb.Controllers;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.pi.tobeeb.Entities.ERole;
import com.pi.tobeeb.Jwt.JwtUtils;
import com.pi.tobeeb.Entities.Role;
import com.pi.tobeeb.Entities.User;
import com.pi.tobeeb.Payload.request.LoginRequest;
import com.pi.tobeeb.Payload.request.SingupRequest;
import com.pi.tobeeb.Payload.response.JwtResponse;
import com.pi.tobeeb.Payload.response.MessageResponse;
import com.pi.tobeeb.Repositorys.RoleRepository;
import com.pi.tobeeb.Repositorys.UserRepository;
import com.pi.tobeeb.Security.UserDetailsImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImp userDetails = (UserDetailsImp) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getUser().getIdUser(), userDetails.getUsername(),
                userDetails.getUser().getEmail(), roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SingupRequest signUpRequest) {
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
        Set<Role> roles = new HashSet<>();
        logger.error("iiiiiicccciiiiiiiiii");

        logger.error(String.valueOf(strRoles));

        if (strRoles == null) {

            Role userRole = roleRepository.findByName(ERole.PATIENT)

                    .orElseThrow(() -> new RuntimeException("Error: Role5 is not found."));

            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "patient":
                        Role adminRole = roleRepository.findByName(ERole.PATIENT)
                                .orElseThrow(() -> new RuntimeException("Error: Role3 is not found."));
                        roles.add(adminRole);

                        break;
                    case "doctor":
                        Role modRole = roleRepository.findByName(ERole.DOCTOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role2 is not found."));
                        roles.add(modRole);

                        break;
                    default:

                        Role userRole = roleRepository.findByName(ERole.PHARMACY)
                                .orElseThrow(() -> new RuntimeException("Error: Role1 is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}