package com.pfizer.sacchonapi.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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

    @OneToMany(mappedBy = "consultation")
    private List<MediData> mediData = new ArrayList<>();
}
