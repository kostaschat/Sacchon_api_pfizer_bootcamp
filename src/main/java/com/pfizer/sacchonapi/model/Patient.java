package com.pfizer.sacchonapi.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Patient {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    private boolean hasConsultation;
    private boolean consultationPending;
    private boolean hasDoctor;

    @OneToOne
    @JoinColumn(name= "user_username")
    private ApplicationUser applicationUser;

    @ManyToOne
    @JoinColumn(name= "doctor_id")
    private Doctor doctor;
    @OneToMany(mappedBy = "patient")
    private List<MediData> mediDatas = new ArrayList<>();
    @OneToMany(mappedBy = "patient")
    private List<Consultation> consultations = new ArrayList<>();
}
