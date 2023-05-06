package com.pi.tobeeb.Services;

import com.pi.tobeeb.Entities.Appointment;
import com.pi.tobeeb.Entities.User;
import com.pi.tobeeb.Interfaces.UserS;
import com.pi.tobeeb.Repositorys.UserR;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserSe implements UserS {
    UserR userR;
    @Override
    public List<User> retrieveAllUsers() {
        return userR.findAll();
    }

    @Override
    public User retrieveUser(int idUser) {
        return userR.findById(idUser).get();
    }

}
