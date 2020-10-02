package com.pfizer.sacchonapi.resource.Patient;

import com.pfizer.sacchonapi.exception.BadEntityException;
import com.pfizer.sacchonapi.exception.NotFoundException;
import com.pfizer.sacchonapi.model.ApplicationUser;
import com.pfizer.sacchonapi.model.Patient;
import com.pfizer.sacchonapi.repository.ApplicationUserRepository;
import com.pfizer.sacchonapi.repository.PatientRepository;
import com.pfizer.sacchonapi.repository.util.JpaUtil;
import com.pfizer.sacchonapi.representation.ApplicationUserRepresentation;
import com.pfizer.sacchonapi.resource.MediData.MediDataResourceImpl;
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

public class PatientListResourceImpl extends ServerResource implements PatientListResource{
    public static final Logger LOGGER = Engine.getLogger(MediDataResourceImpl.class);

    private ApplicationUserRepository applicationUserRepository;
    private PatientRepository patientRepository;
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
            patientRepository = new PatientRepository(em);
        } catch (Exception ex) {
            throw new ResourceException(ex);
        }

    }

    @Override
    public List<ApplicationUserRepresentation> getPatients() throws NotFoundException {
        ResourceUtils.checkRoles(this, Shield.doctor, Shield.chiefDoctor);

        long did;

        try {

            Optional<ApplicationUser> user = applicationUserRepository.getCurrent();

            List<ApplicationUserRepresentation> result = new ArrayList<>();

            if (user.get().getRole().getRoleName().equals("doctor")) {
                if (user.isPresent()) {
                    did = user.get().getDoctor().getId();
                } else {
                    LOGGER.config("This doctor cannon be found in the database.");
                    throw new NotFoundException("No doctor with this name.");
                }

                //return the patients a doctor consults
                List<ApplicationUser> users = applicationUserRepository.findMyPatients(did);
                users.forEach(p -> result.add(new ApplicationUserRepresentation(p)));
            }else {
                List<ApplicationUser> users = applicationUserRepository.findAllPatients();
                users.forEach(p -> result.add(new ApplicationUserRepresentation(p)));
            }
            return result;
        } catch (Exception e) {
            throw new NotFoundException("Users not found");
        }
    }

    @Override
    public void errorModify() throws NotFoundException, BadEntityException {
        ResourceUtils.checkRole(this, Shield.patient);
        try {
            Optional<ApplicationUser> user = applicationUserRepository.getCurrent();
            Patient patientOut;

            if (user.isPresent()) {
                patientOut = user.get().getPatient();
                patientOut.setModified(false);
                patientRepository.update(patientOut);
            } else {
                LOGGER.config("This patient cannot be found in the database.");
                throw new NotFoundException("No patient with this name.");
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error when adding a consultation", e);
            throw new ResourceException(e);
        }
    }
}

