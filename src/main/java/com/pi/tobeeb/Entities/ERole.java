package com.pi.tobeeb.Entities;


import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)

public enum ERole {

    ROLE_PATIENT,
    ROLE_ADMIN,
    ROLE_DOCTOR,
    ROLE_PHARMACY,


    ROLE_DELIVERYMAN

}
