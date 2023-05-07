package com.pi.tobeeb.Entities;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pi.tobeeb.Enums.AppointmentStatus;
import com.pi.tobeeb.Enums.TypeAppointment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Appointment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAppointment;
    private TypeAppointment type;
    private LocalDate dateStart;
    private AppointmentStatus status;
    @JsonIgnore
    @ManyToOne
    User patient;

    @JsonIgnore
    @ManyToOne
    User doctor;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consultation_file_id")
    private ConsultationFile consultationFile;



}
