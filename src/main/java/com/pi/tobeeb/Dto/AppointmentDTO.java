package com.pi.tobeeb.Dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pi.tobeeb.Entities.ConsultationFile;
import com.pi.tobeeb.Entities.User;
import com.pi.tobeeb.Enums.AppointmentStatus;
import com.pi.tobeeb.Enums.TypeAppointment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDTO implements Serializable {
    private Long idAppointment;
    private TypeAppointment type;

    private LocalDateTime dateStart;
    private AppointmentStatus status;
    private String patient;
    private Long patientId;
    private String doctor;
    private Long doctorId;


    private Long consultationFileId;



}
