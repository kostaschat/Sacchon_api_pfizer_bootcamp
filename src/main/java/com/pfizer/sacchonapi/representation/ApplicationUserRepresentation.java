package com.pfizer.sacchonapi.representation;

import com.pfizer.sacchonapi.model.ApplicationUser;
import com.pfizer.sacchonapi.security.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApplicationUserRepresentation {
    private String username;
    private String password;
    private Role role;
    private String uri;

    public ApplicationUserRepresentation(ApplicationUser applicationUser) {
        if (applicationUser != null) {
            username = applicationUser.getUsername();
            password = applicationUser.getPassword();
            role = applicationUser.getRole();
            uri =  "http://localhost:9000/v1/user/" + applicationUser.getUsername();
        }
    }

    public ApplicationUser createUser() {
        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setUsername(username);
        applicationUser.setPassword(password);
        applicationUser.setRole(role);
        return applicationUser;
    }
}
