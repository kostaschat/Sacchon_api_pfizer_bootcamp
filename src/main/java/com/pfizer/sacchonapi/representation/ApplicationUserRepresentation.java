package com.pfizer.sacchonapi.representation;

import com.pfizer.sacchonapi.exception.NotFoundException;
import com.pfizer.sacchonapi.model.ApplicationUser;
import com.pfizer.sacchonapi.security.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
public class ApplicationUserRepresentation {
    private String username;
    private String password;
    private Role role;
    private String firstName;
    private String lastName;
    @Column(unique=true)
    private String email;
    private String address;
    private String city;
    private String zipCode;
    private String phoneNumber;
    private Date dob;
    private Date creationDate;

    private long id;

    public ApplicationUserRepresentation(ApplicationUser applicationUser) {
        if (applicationUser != null) {
            username = applicationUser.getUsername();
            password = applicationUser.getPassword();
            role = applicationUser.getRole();
            firstName = applicationUser.getFirstName();
            lastName = applicationUser.getLastName();
            email = applicationUser.getEmail();
            address = applicationUser.getAddress();
            city = applicationUser.getCity();
            zipCode = applicationUser.getZipCode();
            phoneNumber = applicationUser.getPhoneNumber();
            dob = applicationUser.getDob();
            creationDate = applicationUser.getCreationDate();
            id = applicationUser.getId();

//            try {
//                if (role.getRoleName().equals("patient"))
//                    id = applicationUser.getPatient().getId();
//                else
//                    id = applicationUser.getDoctor().getId();
//            } catch (Exception e){
//                System.out.println(e);
//            }
        }
    }

    public ApplicationUser createUser() {
        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setUsername(username);
        applicationUser.setPassword(password);
        applicationUser.setRole(role);
        applicationUser.setFirstName(firstName);
        applicationUser.setLastName(lastName);
        applicationUser.setEmail(email);
        applicationUser.setAddress(address);
        applicationUser.setCity(city);
        applicationUser.setZipCode(zipCode);
        applicationUser.setPhoneNumber(phoneNumber);
        applicationUser.setDob(dob);

        return applicationUser;
    }
}
