package com.pfizer.sacchonapi.model;

import com.pfizer.sacchonapi.security.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @OneToOne(mappedBy = "applicationUser")
    private Patient patient;
}