package com.pi.tobeeb.Controllers;



import com.pi.tobeeb.Dto.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import com.pi.tobeeb.Entities.User;
import com.pi.tobeeb.Repositorys.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UsersController {
    @Autowired
    UserRepository userRepository;


    @GetMapping("/getall")

    public List<UserDTO> getAllUsers(){
        ArrayList<UserDTO> userDTOS = new ArrayList<>();
        List<User> users = userRepository.findAll();
        users.forEach(user -> {
            UserDTO userDTO = new UserDTO();
            userDTO.setIdUser(user.getIdUser());
            userDTO.setUsername(user.getUsername());
            userDTOS.add(userDTO);
        });
        return userDTOS;
    }



}
