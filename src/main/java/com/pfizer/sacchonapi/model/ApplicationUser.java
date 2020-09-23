package com.pfizer.sacchonapi.model;

import com.pfizer.sacchonapi.security.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ApplicationUser{
    @Id
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String city;
    private String zipCode;
    private String phoneNumber;
    private Date dob;
    private LocalDateTime creationDate;
    private boolean active;

    @OneToOne(mappedBy = "applicationUser")
    private Patient patient;

    @OneToOne(mappedBy = "applicationUser")
    private Doctor doctor;

    public ApplicationUser(ApplicationUser applicationUser) {
        this.username = applicationUser.getUsername();
        this.password = applicationUser.getPassword();
        this.role = applicationUser.getRole();
        this.firstName = applicationUser.getFirstName();
        this.lastName = applicationUser.getLastName();
        this.email = applicationUser.getEmail();
        this.active = applicationUser.isActive();
    }
}