package com.pi.tobeeb.Dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pi.tobeeb.Entities.Appointment;
import com.pi.tobeeb.Entities.Prescription;
import com.pi.tobeeb.Entities.Test;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConsultationFileDTO implements Serializable {
    private Long idFile;
    private String doctorNotes;
    private Long prescriptionId;
    private Long appointmentId;
    @DateTimeFormat(pattern = "yyyy-MM-JJ")
    private LocalDateTime appointmentDate;
}
