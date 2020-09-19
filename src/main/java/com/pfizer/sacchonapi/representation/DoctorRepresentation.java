package com.pfizer.sacchonapi.representation;

import com.pfizer.sacchonapi.model.Doctor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor

public class DoctorRepresentation {

    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private String address;
    private String city;
    private String zipCode;
    private String phoneNumber;
    private Date dob;
    private Date creationDate;
    private boolean active;


    public DoctorRepresentation(Doctor doctor)
    {

        if(doctor != null)
        {
            firstName = doctor.getFirstName();
            lastName = doctor.getLastName();
            username = doctor.getUsername();
            email = doctor.getEmail();
            password = doctor.getPassword();
            address = doctor.getAddress();
            city = doctor.getCity();
            zipCode = doctor.getZipCode();
            phoneNumber = doctor.getPhoneNumber();
            dob = doctor.getDob();
            creationDate = doctor.getCreationDate();
            active = doctor.isActive();
        }
    }

    public Doctor createDoctor()
    {
        Doctor doctor = new Doctor();
        doctor.setFirstName(firstName);
        doctor.setLastName(lastName);
        doctor.setUsername(username);
        doctor.setEmail(email);
        doctor.setPassword(password);
        doctor.setAddress(address);
        doctor.setCity(city);
        doctor.setZipCode(zipCode);
        doctor.setPhoneNumber(phoneNumber);
        doctor.setDob(dob);
        doctor.setCreationDate(creationDate);         //create it automatic??
       doctor.setActive(active);
        return doctor;
    }
}
