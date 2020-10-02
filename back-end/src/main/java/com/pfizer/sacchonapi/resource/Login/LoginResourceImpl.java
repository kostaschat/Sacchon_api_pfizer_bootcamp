package com.pfizer.sacchonapi.resource.Login;

import com.pfizer.sacchonapi.exception.BadEntityException;
import com.pfizer.sacchonapi.exception.NotFoundException;
import com.pfizer.sacchonapi.model.ApplicationUser;
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
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginResourceImpl extends ServerResource implements LoginResource {

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
    public String findUser(ApplicationUserRepresentation reprIn) throws BadEntityException {

        ResourceValidator.notNull(reprIn);
        ResourceValidator.validateLogin(reprIn);

        ApplicationUser applicationUser = reprIn.createUser();

        return applicationUserRepository.findByDetails(applicationUser.getUsername(),applicationUser.getPassword());
    }

    @Override
    public boolean updateUser(ApplicationUserRepresentation applicationUserRepresentation) throws NotFoundException, BadEntityException {
        LOGGER.finer("Update User");

        ResourceUtils.checkRoles(this, Shield.patient, Shield.doctor, Shield.chiefDoctor);
        LOGGER.finer("User allowed to update his account.");
        ApplicationUser applicationUser = applicationUserRepresentation.createUser();
        try {
            Optional<ApplicationUser> user = applicationUserRepository.getCurrent();
            ApplicationUser userOut;

            if (user.isPresent()) {
                userOut = user.get();

                userOut.setAddress(applicationUser.getAddress());
                userOut.setEmail(applicationUser.getEmail());
                userOut.setFirstName(applicationUser.getFirstName());
                userOut.setLastName(applicationUser.getLastName());
                userOut.setPassword(applicationUser.getPassword());

            }else {
                LOGGER.config("This user cannot be found in the database.");
                throw new NotFoundException("No user with this name");
            }

            Optional<ApplicationUser> apUserOut = applicationUserRepository.save(userOut);
            LOGGER.finer("User successfully removed his account.");

            return apUserOut.isPresent();

        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error when updating account", e);
            throw new ResourceException(e);
        }
    }
}
