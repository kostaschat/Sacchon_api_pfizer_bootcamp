package com.pfizer.sacchonapi.resource.Patient;

import com.pfizer.sacchonapi.exception.NotFoundException;
import com.pfizer.sacchonapi.model.ApplicationUser;
import com.pfizer.sacchonapi.model.Patient;
import com.pfizer.sacchonapi.repository.ApplicationUserRepository;
import com.pfizer.sacchonapi.repository.DoctorRepository;
import com.pfizer.sacchonapi.repository.PatientRepository;
import com.pfizer.sacchonapi.repository.util.JpaUtil;
import com.pfizer.sacchonapi.representation.ApplicationUserRepresentation;
import com.pfizer.sacchonapi.resource.MediData.MediDataResourceImpl;
import com.pfizer.sacchonapi.security.ResourceUtils;
import com.pfizer.sacchonapi.security.Shield;
import org.restlet.Request;
import org.restlet.engine.Engine;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class PatientListResourceImpl extends ServerResource implements PatientListResource{
    public static final Logger LOGGER = Engine.getLogger(MediDataResourceImpl.class);

    private ApplicationUserRepository applicationUserRepository;
    private PatientRepository patientRepository;
    private DoctorRepository doctorRepository;
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
            doctorRepository = new DoctorRepository(em);
        } catch (Exception ex) {
            throw new ResourceException(ex);
        }

    }

    @Override
    public List<ApplicationUserRepresentation> getPatients() throws NotFoundException {
        ResourceUtils.checkRole(this, Shield.doctor);

        List<Patient> patients;
        long did;

        try {

            Request request = Request.getCurrent();
            String currentUser = request.getClientInfo().getUser().getName();
            Optional<ApplicationUser> user = applicationUserRepository.findByUsername(currentUser);

            if (user.isPresent()) {
                did = user.get().getDoctor().getId();
            } else {
                LOGGER.config("This doctor cannon be found in the database:" + currentUser);
                throw new NotFoundException("No doctor with name: " + currentUser);
            }

            //return the patients a doctor consults
            List<ApplicationUser> users = applicationUserRepository.findMyPatients(did);
            List<ApplicationUserRepresentation> result = new ArrayList<>();

            users.forEach(p -> result.add(new ApplicationUserRepresentation(p)));

            return result;
        } catch (Exception e) {
            throw new NotFoundException("Users not found");
        }
    }
}

