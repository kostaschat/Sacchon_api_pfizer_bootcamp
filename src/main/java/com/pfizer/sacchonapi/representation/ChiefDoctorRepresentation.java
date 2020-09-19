package com.pfizer.sacchonapi.representation;

import com.pfizer.sacchonapi.model.ChiefDoctor;
import com.pfizer.sacchonapi.model.Patient;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@NoArgsConstructor
public class ChiefDoctorRepresentation {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private String address;
    private String city;
    private String zipCode;
    private String phoneNumber;

    private String uri;

    public ChiefDoctorRepresentation(ChiefDoctor chiefDoctor){
        if (chiefDoctor != null) {
            firstName = chiefDoctor.getFirstName();
            lastName = chiefDoctor.getLastName();
            username = chiefDoctor.getUsername();

            email = chiefDoctor.getEmail();
            password = chiefDoctor.getPassword();
            address = chiefDoctor.getAddress();
            city = chiefDoctor.getCity();
            zipCode = chiefDoctor.getZipCode();
            phoneNumber = chiefDoctor.getPhoneNumber();

            uri = "http://localhost:9000/v1/chief-doctor/" + chiefDoctor.getId();
        }
    }

    public ChiefDoctor createChiefDoctor()
    {
        ChiefDoctor chiefDoctor = new ChiefDoctor();
        chiefDoctor.setFirstName(firstName);
        chiefDoctor.setLastName(lastName);
        chiefDoctor.setUsername(username);

        chiefDoctor.setEmail(email);
        chiefDoctor.setPassword(password);
        chiefDoctor.setAddress(address);
        chiefDoctor.setCity(city);
        chiefDoctor.setZipCode(zipCode);
        chiefDoctor.setPhoneNumber(phoneNumber);


        return chiefDoctor;
    }

}
