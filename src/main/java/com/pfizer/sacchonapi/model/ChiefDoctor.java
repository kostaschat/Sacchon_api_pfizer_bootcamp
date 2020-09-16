package com.pfizer.sacchonapi.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class ChiefDoctor {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    @Column(unique=true)
    private String username;
    @Column(unique=true)
    private String email;
    private String password;
    private String address;
    private String city;
    private String zipCode;
    private String phoneNumber;
}
