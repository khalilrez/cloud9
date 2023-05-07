package com.pi.tobeeb.Controllers;

import com.pi.tobeeb.Entities.Appointment;
import com.pi.tobeeb.Entities.User;
import com.pi.tobeeb.Services.UserSe;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor

@RestController
public class UserC {
    @Autowired
    UserSe userSe;
    @GetMapping("/user")

    public List<User> getAppointment()
    {
        return userSe.retrieveAllUsers();

    }

    @GetMapping("/user/{id}")

    public User AfficherUsertById(@PathVariable("id") int iduser)
    {
        return userSe.retrieveUser(iduser);
    }
}
