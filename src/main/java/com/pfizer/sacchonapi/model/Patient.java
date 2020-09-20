package com.pfizer.sacchonapi.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Patient {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @Column(unique=true)
    private String username;
    @Column(unique=true)
    @NotNull
    private String email;
    @NotNull
    private String password;
    private String address;
    private String city;
    private String zipCode;
    private String phoneNumber;
    private Date dob;
    private Date creationDate;

    private boolean active;
    private boolean hasConsultation;
    private boolean consultationPending;
    private boolean hasDoctor;

    @ManyToOne
    @JoinColumn(name= "doctor_id")
    private Doctor doctor;
    @OneToMany(mappedBy = "patient")
    private List<MediData> mediDatas = new ArrayList<>();
    @OneToMany(mappedBy = "patient")
    private List<Consultation> consultations = new ArrayList<>();
}
