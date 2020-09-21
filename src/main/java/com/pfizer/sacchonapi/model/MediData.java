package com.pfizer.sacchonapi.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;


@Data
@Entity

public class MediData {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private double glucose;
    private double carb;
    private Date measuredDate;
    @ManyToOne
    @JoinColumn(name= "patient_id")
    private Patient patient;
}
