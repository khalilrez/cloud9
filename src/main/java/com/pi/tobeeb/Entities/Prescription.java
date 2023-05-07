package com.pi.tobeeb.Entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPrescription;
    private String content;
    private LocalDate creationDate;


    @OneToOne(mappedBy = "prescription", cascade = CascadeType.ALL)
    private ConsultationFile consultationFile;


}
