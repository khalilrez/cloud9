package com.pi.tobeeb.Entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(	name = "user",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class User  {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)

 private Long idUser;
 private String username;
 private String email;
 private String password;
 private String phonenumber;
 //private String Location;
 private String picture;
 private String verificationToken;
 private int isverified;
 private String userCode;

 public String getUserCode() {
  return userCode;
 }

 public void setUserCode(String userCode) {
  this.userCode = userCode;
 }

 @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
 @JoinTable(name = "userRoles",
         joinColumns = {
                 @JoinColumn(name = "id_User")
         },
         inverseJoinColumns = {
                 @JoinColumn(name = "id_Role")
         }
 )
 private Set<Role> role = new HashSet<>();
 @JsonIgnore
 @OneToMany(cascade = CascadeType.ALL, mappedBy ="patient")
 private Set<Appointment> appointment;

 @OneToMany(cascade = CascadeType.ALL, mappedBy ="user")
 private Set<Reclamation> reclamation;



 public User(String username, String email, String password) {
  this.username = username;
  this.email = email;
  this.password = password;
 }
 public Long getId() {
  return idUser;
 }

 public void setId(long id) {
  this.idUser = id;
 }

 public String getUsername() {
  return username;
 }

 public void setUsername(String username) {
  this.username = username;
 }

 public String getEmail() {
  return email;
 }

 public void setEmail(String email) {
  this.email = email;
 }

 public String getPassword() {
  return password;
 }

 public void setPassword(String password) {
  this.password = password;
 }

 public Set<Role> getRoles() {
  return role;
 }

 public void setRoles(Set<Role> roles) {
  this.role = roles;
 }

 public String getVerificationToken() {
  return verificationToken;
 }

 public void setVerificationToken(String verificationToken) {
  this.verificationToken = verificationToken;
 }

 public int getIsverified() {
  return isverified;
 }

 public void setIsverified(int isverified) {
  this.isverified = isverified;
 }
}
