package com.pfizer.sacchonapi.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class ChiefDoctor{
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
}
