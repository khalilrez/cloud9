package com.pi.tobeeb.Entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

import java.util.Date;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity

@Setter
//@NoArgsConstructor
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


 private String speciality;
 private String gender;
 private String height;
 private String weight;
 private String bloodType;
 private String age;
 private String education;
 @Column(name = "certificate",columnDefinition = "longtext")
 private String certificate;
 private String firstName;
 private String lastName;
 private String hourForWorkingStart;
 private String hourForWorkingEnd;
 private String city;
 private String postCode;
 //private String Location;
 @Column(name = "imageProfile",columnDefinition = "longtext")

 private String imageProfile;
 private String verificationToken;
 private int isverified;
 private String userCode;
 @Column(name = "account_non_locked")
 private boolean accountNonLocked= true;

 @Column(name = "failed_attempt")
 private int failedAttempt;

 @Column(name = "lock_time")
 private Date lockTime;
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

 public User() {

 }

 public String getUserCode() {
  return userCode;
 }

 public boolean isAccountNonLocked() {
  return accountNonLocked;
 }

 public void setAccountNonLocked(boolean accountNonLocked) {
  this.accountNonLocked = accountNonLocked;
 }

 public int getFailedAttempt() {
  return failedAttempt;
 }

 public void setFailedAttempt(int failedAttempt) {
  this.failedAttempt = failedAttempt;
 }

 public Date getLockTime() {
  return lockTime;
 }

 public void setLockTime(Date lockTime) {
  this.lockTime = lockTime;
 }

 public void setUserCode(String userCode) {
  this.userCode = userCode;
 }



 @JsonIgnore

 @OneToMany(cascade = CascadeType.ALL, mappedBy ="patient")
 private Set<Appointment> appointmentP;
 @JsonIgnore

 @OneToMany(cascade = CascadeType.ALL, mappedBy ="doctor")
 private Set<Appointment> appointmentD;
 @OneToMany(cascade = CascadeType.ALL, mappedBy ="user")
 private Set<Order2> order2s;

 @OneToMany(mappedBy = "user1", cascade = CascadeType.ALL, orphanRemoval = true)
 private Set<Conversation> conversationAsUser1 = new HashSet<>();

 @OneToMany(mappedBy = "user2", cascade = CascadeType.ALL, orphanRemoval = true)
 private Set<Conversation> conversationAsUser2 = new HashSet<>();


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

 public String getGender() {
  return gender;
 }

 public void setGender(String gender) {
  this.gender = gender;
 }

 public String getHeight() {
  return height;
 }

 public void setHeight(String height) {
  this.height = height;
 }

 public String getWeight() {
  return weight;
 }

 public void setWeight(String weight) {
  this.weight = weight;
 }

 public String getBloodType() {
  return bloodType;
 }

 public void setBloodType(String bloodType) {
  this.bloodType = bloodType;
 }

 public String getAge() {
  return age;
 }

 public void setAge(String age) {
  this.age = age;
 }

 public String getEducation() {
  return education;
 }

 public void setEducation(String education) {
  this.education = education;
 }

 public String getCertificate() {
  return certificate;
 }

 public void setCertificate(String certificate) {
  this.certificate = certificate;
 }

 public String getFirstName() {
  return firstName;
 }

 public void setFirstName(String firstName) {
  this.firstName = firstName;
 }

 public String getLastName() {
  return lastName;
 }

 public void setLastName(String lastName) {
  this.lastName = lastName;
 }

 public String getHourForWorkingStart() {
  return hourForWorkingStart;
 }

 public void setHourForWorkingStart(String hourForWorkingStart) {
  this.hourForWorkingStart = hourForWorkingStart;
 }

 public String getHourForWorkingEnd() {
  return hourForWorkingEnd;
 }

 public void setHourForWorkingEnd(String hourForWorkingEnd) {
  this.hourForWorkingEnd = hourForWorkingEnd;
 }

 public String getCity() {
  return city;
 }

 public void setCity(String city) {
  this.city = city;
 }

 public String getPostCode() {
  return postCode;
 }

 public void setPostCode(String postCode) {
  this.postCode = postCode;
 }
}
