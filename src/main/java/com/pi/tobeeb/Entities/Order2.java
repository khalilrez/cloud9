package com.pi.tobeeb.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order2 implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idOrder;
    private String orderItems;

   // @Enumerated(EnumType.STRING)
    private String Status;
    private double totalprice;
    private int qte;

    @JsonIgnore
    @ManyToOne
    private Delivery delivery;
    @JsonIgnore
    @ManyToOne
    User user;

}