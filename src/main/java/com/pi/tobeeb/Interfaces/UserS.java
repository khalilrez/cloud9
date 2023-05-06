package com.pi.tobeeb.Interfaces;

import com.pi.tobeeb.Entities.Appointment;
import com.pi.tobeeb.Entities.User;

import java.util.List;

public interface UserS {
    List<User> retrieveAllUsers();
    User retrieveUser (int idUser);

}
