package com.pi.tobeeb.Controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
    private Logger logger = LoggerFactory.getLogger(AuthController.class);
    @GetMapping("/all")
    public String allAccess() {
        return "user Content.";
    }

    @GetMapping("/patient")
    @PreAuthorize("hasRole('ROLE_PATIENT')")
    public String userAccess() {

        logger.error("iiiiiicccjjjciiiiiiiiii");


        return "Patient Content.";
    }

    @GetMapping("/doctor")
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public String moderatorAccess() {
        logger.error("iiiiiicccciiiiiiiiii");
        return "doctor Board.";
    }

    @GetMapping("/pharmacy")
    @PreAuthorize("hasRole('ROLE_PHARMACY')")
    public String adminAccess() {
        return "Pharmacy Board.";
    }
}
