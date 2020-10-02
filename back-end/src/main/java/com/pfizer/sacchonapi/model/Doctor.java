package com.pfizer.sacchonapi.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Doctor {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name= "user_username")
    private ApplicationUser applicationUser;

    @OneToMany(mappedBy = "doctor")
    private List<Patient> patients = new ArrayList<>();
    @OneToMany(mappedBy = "doctor")
    private List<Consultation> consultations = new ArrayList<>();
}
