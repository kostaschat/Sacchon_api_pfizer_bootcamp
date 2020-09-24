package com.pfizer.sacchonapi.resource;

import com.pfizer.sacchonapi.exception.BadEntityException;
import com.pfizer.sacchonapi.model.ApplicationUser;
import com.pfizer.sacchonapi.repository.ApplicationUserRepository;
import com.pfizer.sacchonapi.repository.DoctorRepository;
import com.pfizer.sacchonapi.repository.PatientRepository;
import com.pfizer.sacchonapi.repository.util.JpaUtil;
import com.pfizer.sacchonapi.representation.ApplicationUserRepresentation;
import com.pfizer.sacchonapi.resource.util.ResourceValidator;
import org.restlet.engine.Engine;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import javax.persistence.EntityManager;
import java.util.logging.Logger;

public class LoginResourceImpl extends ServerResource implements LoginResource{

    public static final Logger LOGGER = Engine.getLogger(LoginResourceImpl.class);

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
    public boolean findUser(ApplicationUserRepresentation reprIn) throws BadEntityException {

        ResourceValidator.notNull(reprIn);
        ResourceValidator.validateLogin(reprIn);

        ApplicationUser applicationUser = reprIn.createUser();

        return applicationUserRepository.findByDetails(applicationUser.getUsername(),applicationUser.getPassword());

    }
}
