package com.pfizer.sacchonapi.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;


@Data
@Entity
public class Consultation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String medicationName;
    private double dosage;
    private Date ConsultationDate;

    @ManyToOne
    @JoinColumn(name= "doctor_id")
    private Doctor doctor;
    @ManyToOne
    @JoinColumn(name= "patient_id")
    private Patient patient;
}
