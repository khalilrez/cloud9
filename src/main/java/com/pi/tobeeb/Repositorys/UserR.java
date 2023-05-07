package com.pi.tobeeb.Repositorys;

import com.pi.tobeeb.Entities.Appointment;
import com.pi.tobeeb.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserR extends JpaRepository<User, Integer> {

}
