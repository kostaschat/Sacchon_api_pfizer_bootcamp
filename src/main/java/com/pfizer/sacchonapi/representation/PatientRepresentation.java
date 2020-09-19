package com.pfizer.sacchonapi.representation;

import com.pfizer.sacchonapi.model.Patient;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
public class PatientRepresentation {

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
    private boolean hasConsultation;
    private boolean consultationPending;
    private boolean hasDoctor;

    private String uri;

    public PatientRepresentation(Patient patient)
    {

        if(patient != null)
        {
            firstName = patient.getFirstName();
            lastName = patient.getLastName();
            username = patient.getUsername();

            email = patient.getEmail();
            password = patient.getPassword();
            address = patient.getAddress();
            city = patient.getCity();
            zipCode = patient.getZipCode();
            phoneNumber = patient.getPhoneNumber();
            dob = patient.getDob();
            creationDate = patient.getCreationDate();

            active = patient.isActive();
            hasConsultation = patient.isHasConsultation();
            consultationPending = patient.isConsultationPending();
            hasDoctor = patient.isHasDoctor();

            uri =  "http://localhost:9000/v1/patient/" + patient.getId();
        }
    }

    public Patient createPatient()
    {
        Patient patient = new Patient();
        patient.setFirstName(firstName);
        patient.setLastName(lastName);
        patient.setUsername(username);

        patient.setEmail(email);
        patient.setPassword(password);
        patient.setAddress(address);
        patient.setCity(city);
        patient.setZipCode(zipCode);
        patient.setPhoneNumber(phoneNumber);
        patient.setDob(dob);
        patient.setCreationDate(creationDate);

        patient.setActive(active);
        patient.setHasConsultation(hasConsultation);
        patient.setConsultationPending(consultationPending);
        patient.setHasDoctor(hasDoctor);

        return patient;
    }
}
