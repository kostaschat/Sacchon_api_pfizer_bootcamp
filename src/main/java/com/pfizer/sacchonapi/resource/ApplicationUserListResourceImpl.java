package com.pfizer.sacchonapi.resource;

import com.pfizer.sacchonapi.exception.BadEntityException;
import com.pfizer.sacchonapi.exception.NotFoundException;
import com.pfizer.sacchonapi.model.ApplicationUser;
import com.pfizer.sacchonapi.model.Doctor;
import com.pfizer.sacchonapi.model.Patient;
import com.pfizer.sacchonapi.repository.ApplicationUserRepository;
import com.pfizer.sacchonapi.repository.DoctorRepository;
import com.pfizer.sacchonapi.repository.PatientRepository;
import com.pfizer.sacchonapi.repository.util.JpaUtil;
import com.pfizer.sacchonapi.representation.ApplicationUserRepresentation;
import com.pfizer.sacchonapi.resource.util.ResourceValidator;
import com.pfizer.sacchonapi.security.ResourceUtils;
import com.pfizer.sacchonapi.security.Role;
import com.pfizer.sacchonapi.security.Shield;
import org.restlet.Request;
import org.restlet.engine.Engine;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ApplicationUserListResourceImpl extends ServerResource implements ApplicationUserListResource {

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

            if (customerOptOut.isPresent()) {

                userOut = customerOptOut.get();

                if (userOut.getRole() == Role.patient) {
                    Patient patient = new Patient();
                    patient.setHasConsultation(false);
                    patient.setHasDoctor(false);
                    patient.setConsultationPending(false);
                    patient.setApplicationUser(userOut);
                    patientRepository.save(patient);
                } else if (userOut.getRole() == Role.doctor){
                    Doctor doctor = new Doctor();
                    doctor.setApplicationUser(userOut);
                    doctorRepository.save(doctor);
                }
            } else
                throw new BadEntityException("User has not been created");

            ApplicationUserRepresentation result = new ApplicationUserRepresentation(userOut);

            return result;
        } catch (Exception ex) {

            throw new ResourceException(ex);
        }
    }

    /**
     *
     * @return The patients a doctor consults and the new patients in the system
     * @throws NotFoundException
     */
    @Override
    public List<ApplicationUserRepresentation> getUsers() throws NotFoundException {

        ResourceUtils.checkRole(this, Shield.doctor);

        List<Patient> patients;
        long did;

        try {

            //find the id of this doctor
            Request request = Request.getCurrent();
            String currentUser = request.getClientInfo().getUser().getName();
            Optional<ApplicationUser> user = applicationUserRepository.findByUsername(currentUser);
            Patient patient = null;

            if (user.isPresent()) {
               did = user.get().getDoctor().getId();
            } else {
                LOGGER.config("This doctor cannon be found in the database:" + currentUser);
                throw new NotFoundException("No doctor with name: " + currentUser);
            }

            //return the patients a doctor consults
            List<ApplicationUser> users = applicationUserRepository.findDoctorsPatients(did);
            List<ApplicationUserRepresentation> result = new ArrayList<>();
           // users.forEach(user -> result.add(new ApplicationUserRepresentation(user)));
            return result;
        } catch (Exception e) {
            throw new NotFoundException("Users not found");
        }
    }
}
