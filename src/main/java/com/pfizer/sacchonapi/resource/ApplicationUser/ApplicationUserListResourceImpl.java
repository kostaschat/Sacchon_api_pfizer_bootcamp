package com.pfizer.sacchonapi.resource.ApplicationUser;

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
import com.pfizer.sacchonapi.resource.MediData.MediDataResourceImpl;
import com.pfizer.sacchonapi.resource.util.ResourceValidator;
import com.pfizer.sacchonapi.security.ResourceUtils;
import com.pfizer.sacchonapi.security.Role;
import com.pfizer.sacchonapi.security.Shield;
import org.restlet.engine.Engine;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class ApplicationUserListResourceImpl extends ServerResource implements ApplicationUserListResource {

    public static final Logger LOGGER = Engine.getLogger(MediDataResourceImpl.class);

    private ApplicationUserRepository applicationUserRepository;
    private PatientRepository patientRepository;
    private DoctorRepository doctorRepository;
    private EntityManager em;

    private String fromDate;
    private String toDate;

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
            fromDate = getAttribute("fromdate");
            toDate = getAttribute("todate");
        } catch (Exception ex) {
            fromDate = null;
            toDate = null;
            throw new ResourceException(ex);
        }

    }


    @Override
    public ApplicationUserRepresentation add(ApplicationUserRepresentation userIn) throws BadEntityException {


        ResourceValidator.notNull(userIn);
        ResourceValidator.validate(userIn);

        try {

            ApplicationUser applicationUser = userIn.createUser();
            applicationUser.setActive(true);
            Date date = new Date();
            applicationUser.setCreationDate(date);

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
                    userOut.setId(patient.getId());
                } else if (userOut.getRole() == Role.doctor) {
                    Doctor doctor = new Doctor();
                    doctor.setApplicationUser(userOut);
                    doctorRepository.save(doctor);
                    userOut.setId(doctor.getId());
                }
            } else
                throw new BadEntityException("User has not been created");

            applicationUserRepository.save(userOut);
            ApplicationUserRepresentation result;
            result = new ApplicationUserRepresentation(userOut);

            return result;
        } catch (Exception ex) {
            throw new ResourceException(ex);
        }
    }

    /**
     * @return The patients a doctor consults and the new patients in the system
     * @throws NotFoundException
     */
    @Override
    public List<ApplicationUserRepresentation> getUsers() throws NotFoundException {

        ResourceUtils.checkRoles(this, Shield.doctor, Shield.chiefDoctor);

        try {
            List<ApplicationUser> users = new ArrayList<>();
            List<ApplicationUserRepresentation> result = new ArrayList<>();

            Optional<ApplicationUser> user = applicationUserRepository.getCurrent();

            if (fromDate != null && toDate != null) {
                if (user.isPresent()) {
                    if (user.get().getRole().getRoleName().equals("chiefDoctor"))
                        users = applicationUserRepository.findInactivePatients(fromDate, toDate);
                } else {
                    LOGGER.config("This user cannot be found in the database.");
                    throw new NotFoundException("No user with this name.");
                }
            } else {

                if (user.isPresent()) {
                    if (user.get().getRole().getRoleName().equals("doctor"))
                        users = applicationUserRepository.findAvailablePatients();
                } else {
                    LOGGER.config("This user cannot be found in the database.");
                    throw new NotFoundException("No user with this name.");
                }
            }

            users.forEach(p -> result.add(new ApplicationUserRepresentation(p)));

            return result;
        } catch (Exception e) {
            throw new NotFoundException("Users not found");
        }
    }
}
