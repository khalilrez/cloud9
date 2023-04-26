package com.pi.tobeeb.Entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private TypeAppointment Type;
    private LocalDate dateStart;
    private LocalDate dateEnd;

    @JsonIgnore
    @ManyToOne
    User patient;

    @JsonIgnore
    @ManyToOne
    User doctor;

}
