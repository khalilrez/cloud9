package com.pi.tobeeb.Entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class User implements Serializable {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
 @Column(name = "id_user")
 private int idUser;
 private String username;
 private String email;
 private String password;
 private String role;
 private String phonenumber;
 //private String Location;
 private String picture;
 @JsonIgnore
 @OneToMany(cascade = CascadeType.ALL, mappedBy ="patient")
 private Set<Appointment> appointment;

 @OneToMany(cascade = CascadeType.ALL, mappedBy ="user")
 private Set<Reclamation> reclamation;

/*
 @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
 private List<Post> posts = new ArrayList<>();*/
}
