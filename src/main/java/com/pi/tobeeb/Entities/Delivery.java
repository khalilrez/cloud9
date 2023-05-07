package com.pi.tobeeb.Entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Date;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;


import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Delivery implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int idDelivery;
    private String customerName;
    private String customerAddress;
    @DateTimeFormat(pattern = "yyyy-MM-JJ")
    private Date deliveryDate;
    private String status;
    private double price;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "delivery")
    private Set<Order2> orders;
}
