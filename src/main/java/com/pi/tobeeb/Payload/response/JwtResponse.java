package com.pi.tobeeb.Payload.response;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;
    private List<String> roles;


    private String imageProfile;
    private int statusCode;
    private String password;
    private String phonenumber;
    private String speciality;
    private String gender;
    private String height;
    private String weight;
    private String bloodType;
    private String age;
    private String education;
    private String certificate;
    private String firstName;
    private String lastName;
    private String hourForWorkingStart;
    private String hourForWorkingEnd;
    private String city;
    private String postCode;

    public JwtResponse(String jwt, Long idUser, String username, String email, List<String> roles, String imageProfile, String phonenumber, String speciality, String gender, String height, String weight, String bloodType, String age, String education, String certificate, String firstName, String lastName, String hourForWorkingStart, String hourForWorkingEnd, String city, String postCode) {
        this.token= jwt;
        this.id = idUser;
        this.username = username;
        this.email = email;
        this.roles=roles;
        this.imageProfile = imageProfile;
        this.phonenumber = phonenumber;
        this.speciality = speciality;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.bloodType = bloodType;
        this.age = age;
        this.education = education;
        this.certificate = certificate;
        this.firstName = firstName;
        this.lastName = lastName;
        this.hourForWorkingStart = hourForWorkingStart;
        this.hourForWorkingEnd = hourForWorkingEnd;
        this.city = city;
        this.postCode = postCode;
    }

    public String getToken() {
        return token;
    }

    public JwtResponse(String accessToken,Long id, String username, String email, List<String> roles,String imageProfile, String password, String phonenumber, String speciality, String gender, String height, String weight, String bloodType, String age, String education, String certificate, String firstName, String lastName, String hourForWorkingStart, String hourForWorkingEnd, String city, String postCode) {
        this.token= accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles=roles;
        this.imageProfile = imageProfile;
        this.phonenumber = phonenumber;
        this.speciality = speciality;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.bloodType = bloodType;
        this.age = age;
        this.education = education;
        this.certificate = certificate;
        this.firstName = firstName;
        this.lastName = lastName;
        this.hourForWorkingStart = hourForWorkingStart;
        this.hourForWorkingEnd = hourForWorkingEnd;
        this.city = city;
        this.postCode = postCode;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
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

    public JwtResponse(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getImageProfile() {
        return imageProfile;
    }

    public void setImageProfile(String imageProfile) {
        this.imageProfile = imageProfile;
    }

    public JwtResponse(String accessToken, Long id, String username, String email, List<String> roles, String img) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;

        this.imageProfile=img;
    }


    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }
}