package com.pfizer.sacchonapi.resource;

import com.pfizer.sacchonapi.exception.BadEntityException;
import com.pfizer.sacchonapi.exception.NotFoundException;
import com.pfizer.sacchonapi.model.ApplicationUser;
import com.pfizer.sacchonapi.model.Patient;
import com.pfizer.sacchonapi.repository.ApplicationUserRepository;
import com.pfizer.sacchonapi.repository.util.JpaUtil;
import com.pfizer.sacchonapi.representation.ApplicationUserRepresentation;
import com.pfizer.sacchonapi.resource.util.ResourceValidator;
import com.pfizer.sacchonapi.security.ResourceUtils;
import com.pfizer.sacchonapi.security.Shield;
import org.restlet.engine.Engine;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ApplicationUserListResourceImpl extends ServerResource implements ApplicationUserListResource{

    private ApplicationUserRepository applicationUserRepository;
    private EntityManager em;

    @Override
    protected void doRelease() {
        em.close();
    }

    @Override
    protected void doInit() {

        try {
            em = JpaUtil.getEntityManager();
            applicationUserRepository = new ApplicationUserRepository(em);
        } catch (Exception ex) {
            throw new ResourceException(ex);
        }

    }


    @Override
    public ApplicationUserRepresentation add(ApplicationUserRepresentation userIn) throws BadEntityException {

        // Check authorization
//        ResourceUtils.checkRole(this, Shield.ROLE_CHIEF_DOCTOR);

        ResourceValidator.notNull(userIn);
        ResourceValidator.validate(userIn);

        try {

            // Convert CompanyRepresentation to Company
            ApplicationUser applicationUser = userIn.createUser();

            Optional<ApplicationUser> customerOptOut = applicationUserRepository.save(applicationUser);

            ApplicationUser userOut;
            if (customerOptOut.isPresent())
                userOut = customerOptOut.get();
            else
                throw new BadEntityException("User has not been created");

            ApplicationUserRepresentation result = new ApplicationUserRepresentation(userOut);


            return result;
        } catch (Exception ex) {

            throw new ResourceException(ex);
        }
    }

    @Override
    public List<ApplicationUserRepresentation> getUsers() throws NotFoundException {

//        ResourceUtils.checkRole(this, Shield.ROLE_CHIEF_DOCTOR);
        try {
            List<ApplicationUser> users = applicationUserRepository.findAll();
            List<ApplicationUserRepresentation> result = new ArrayList<>();
            users.forEach(user -> result.add(new ApplicationUserRepresentation(user)));
            return result;
        } catch (Exception e) {
            throw new NotFoundException("Users not found");
        }
    }
}