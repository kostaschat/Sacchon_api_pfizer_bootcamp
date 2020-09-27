package com.pfizer.sacchonapi.resource.ApplicationUser;

import com.pfizer.sacchonapi.exception.BadEntityException;
import com.pfizer.sacchonapi.exception.NotFoundException;
import com.pfizer.sacchonapi.model.ApplicationUser;
import com.pfizer.sacchonapi.model.Doctor;
import com.pfizer.sacchonapi.model.Patient;
import com.pfizer.sacchonapi.repository.ApplicationUserRepository;
import com.pfizer.sacchonapi.repository.ConsultationRepository;
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
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ApplicationUserResourceImpl extends ServerResource implements ApplicationUserResource {

    public static final Logger LOGGER = Engine.getLogger(MediDataResourceImpl.class);

    private EntityManager em;

    private ApplicationUserRepository applicationUserRepository;
    private PatientRepository patientRepository;
    private DoctorRepository doctorRepository;
    private ConsultationRepository consultationRepository;
    private String userId;

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
            consultationRepository = new ConsultationRepository(em);

        } catch (Exception ex) {
            throw new ResourceException(ex);
        }

        try {
            userId = getAttribute("pid");
        } catch (Exception e) {
            userId = null;
        }

    }

    @Override
    public ApplicationUserRepresentation consultPatient() throws NotFoundException, BadEntityException {


        ResourceUtils.checkRole(this, Shield.doctor);
        Long uid = 0l;


        try {
            if (userId != null) {
                uid = Long.valueOf(userId).longValue();

            } else {
                LOGGER.config("This user cannon be found:" + uid);
                throw new NotFoundException("No user with id: " + uid);
            }

            Request request = Request.getCurrent();
            String currentUser = request.getClientInfo().getUser().getName();
            Optional<ApplicationUser> user = applicationUserRepository.findByUsername(currentUser);
            Doctor doctorOut = null;

            if (user.isPresent()) {
                doctorOut = user.get().getDoctor();
            } else {
                LOGGER.config("This doctor cannon be found in the database:" + currentUser);
                throw new NotFoundException("No doctor with name : " + currentUser);
            }

            Optional<Patient> patient = patientRepository.findById(uid);
            Patient patientOut = null;

            if (patient.isPresent()) {
                patientOut = patient.get();
                patientOut.setDoctor(doctorOut);
            } else {
                LOGGER.config("This patient cannot be found in the database:" + uid);
                throw new NotFoundException("No patient with id in the database : " + uid);
            }

            Optional<Patient> p = patientRepository.save(patientOut);

            LOGGER.finer("Consultation successfully saved.");
            return new ApplicationUserRepresentation(p.get().getApplicationUser().getPatient().getApplicationUser());

        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error when adding a consultation", e);
            throw new ResourceException(e);
        }

    }

    @Override
    public ApplicationUserRepresentation remove() throws NotFoundException, BadEntityException {

        LOGGER.finer("Remove Account");

        ResourceUtils.checkRoles(this, Shield.patient, Shield.doctor);
        LOGGER.finer("User allowed to remove his account.");
        long did, pid;
        try {
            Request request = Request.getCurrent();
            String currentUser = request.getClientInfo().getUser().getName();
            Optional<ApplicationUser> user = applicationUserRepository.findByUsername(currentUser);
            ApplicationUser userOut;

            if (user.isPresent()) {
                userOut = user.get();
                userOut.setActive(false);
                if(user.get().getRole().equals("doctor"))
                {
                    did = userOut.getDoctor().getId();
                    //find the patients of this doctor and make their doctor id null
                    detachUsersFromThisDoctor(did);
                }

            } else {
                LOGGER.config("This user cannot be found in the database:" + currentUser);
                throw new NotFoundException("No user with name : " + currentUser);
            }

            Optional<ApplicationUser> applicationUser = applicationUserRepository.save(userOut);
            LOGGER.finer("User successfully removed his account.");
            return new ApplicationUserRepresentation(applicationUser.get());

        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error when removing account", e);
            throw new ResourceException(e);
        }

    }


    public void detachUsersFromThisDoctor(long did) throws ResourceException
    {
        List<ApplicationUser> patients = applicationUserRepository.findMyPatients(did);
        for(ApplicationUser patient: patients)
        {
            patient.getPatient().setDoctor(null);
            patient.getPatient().setHasDoctor(false);
            applicationUserRepository.save(patient);
        }
    }
}