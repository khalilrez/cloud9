package com.pi.tobeeb.Entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "prohibited_words")
public class ProhibitedWord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "word")
    private String word;

    // getters and setters

}




