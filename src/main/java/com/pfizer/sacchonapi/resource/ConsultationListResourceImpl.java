package com.pfizer.sacchonapi.resource;

import com.pfizer.sacchonapi.exception.BadEntityException;
import com.pfizer.sacchonapi.exception.NotFoundException;
import com.pfizer.sacchonapi.model.ApplicationUser;
import com.pfizer.sacchonapi.model.Consultation;
import com.pfizer.sacchonapi.model.Doctor;
import com.pfizer.sacchonapi.model.Patient;
import com.pfizer.sacchonapi.repository.ApplicationUserRepository;
import com.pfizer.sacchonapi.repository.ConsultationRepository;
import com.pfizer.sacchonapi.repository.DoctorRepository;
import com.pfizer.sacchonapi.repository.PatientRepository;
import com.pfizer.sacchonapi.repository.util.JpaUtil;
import com.pfizer.sacchonapi.representation.ConsultationRepresentation;
import com.pfizer.sacchonapi.resource.util.ResourceValidator;
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
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsultationListResourceImpl extends ServerResource implements ConsultationListResource {

    public static final Logger LOGGER = Engine.getLogger(ConsultationResource.class);

    private ConsultationRepository consultationRepository;
    private PatientRepository patientRepository;
    private DoctorRepository doctorRepository;
    private ApplicationUserRepository applicationUserRepository;
    private String startDate;
    private String endDate;
    private long p_id;

    private EntityManager em;


    @Override
    protected void doRelease() {
        em.close();
    }

    @Override
    protected void doInit() {
        LOGGER.info("Initialising consultation resource starts");
        try {
            em = JpaUtil.getEntityManager();
            consultationRepository = new ConsultationRepository(em);
            patientRepository = new PatientRepository(em);
            applicationUserRepository = new ApplicationUserRepository(em);
            p_id = Long.parseLong(getAttribute("pid"));
        } catch (Exception e) {
            p_id = -1;
        }
        LOGGER.info("Initialising consultation resource ends");
    }

    public List<ConsultationRepresentation> getConsultations() throws NotFoundException {

        LOGGER.finer("Select all consultations.");

        ResourceUtils.checkRoles(this, Shield.patient, Shield.doctor, Shield.chiefDoctor);

        try {
            List<Consultation> consultations;
            List<ConsultationRepresentation> result = new ArrayList<>();
            if (p_id != -1) {
                Request request = Request.getCurrent();
                String username = request.getClientInfo().getUser().getName();
                Optional<ApplicationUser> user = applicationUserRepository.findByUsername(username);

                Doctor doctorOut;
                if (user.isPresent()) {
                    doctorOut = user.get().getDoctor();
                } else {
                    LOGGER.config("This doctor cannot be found in the database:" + username);
                    throw new NotFoundException("No doctor with name : " + username);
                }

                long d_id = doctorOut.getId();
                consultations = consultationRepository.findPatientCons(p_id,d_id);
            } else {
                Request request = Request.getCurrent();
                String username = request.getClientInfo().getUser().getName();
                Optional<ApplicationUser> user = applicationUserRepository.findByUsername(username);

                Patient patientOut;
                if (user.isPresent()) {
                    patientOut = user.get().getPatient();
                } else {
                    LOGGER.config("This patient cannot be found in the database:" + username);
                    throw new NotFoundException("No patient with name : " + username);
                }

                long id = patientOut.getId();
                consultations = consultationRepository.findPatientCons(id);
            }
            consultations.forEach(consultation -> result.add(new ConsultationRepresentation(consultation)));

            return result;
        } catch (Exception e) {
            throw new NotFoundException("consultations not found");
        }
    }

    @Override
    public ConsultationRepresentation add(ConsultationRepresentation consultationIn) throws BadEntityException {
        LOGGER.finer("Add a new consultation.");

        ResourceUtils.checkRole(this, Shield.doctor);
        LOGGER.finer("User allowed to add a consultation.");

        ResourceValidator.notNull(consultationIn);
        ResourceValidator.validate(consultationIn);
        LOGGER.finer("Consultation checked");

        try {

            Consultation consultation = consultationIn.createConsulation();

            Optional<Consultation> consultationOptOut = consultationRepository.save(consultation);
            Consultation consultationOut;

            if (consultationOptOut.isPresent()) {
                consultationOut = consultationOptOut.get();

                Patient patientOut;
                Optional<Patient> patientOptional = patientRepository.findById(p_id);

                if (patientOptional.isPresent()) {
                    patientOut = patientOptional.get();
                    consultation.setPatient(patientOut);
                }

                Request request = Request.getCurrent();
                String username = request.getClientInfo().getUser().getName();
                Optional<ApplicationUser> user = applicationUserRepository.findByUsername(username);

                Doctor doctorOut;
                if (user.isPresent()) {
                    doctorOut = user.get().getDoctor();
                } else {
                    LOGGER.config("This doctor cannon be found in the database:" + username);
                    throw new NotFoundException("No doctor with name : " + username);
                }

                consultation.setDoctor(doctorOut);

                consultationRepository.save(consultation);
            } else
                throw new BadEntityException(" Consultation has not been created");

            ConsultationRepresentation result = new ConsultationRepresentation(consultationOut);

            LOGGER.finer("Consultation successfully added.");

            return result;

        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, "Error when adding a consultation", ex);

            throw new ResourceException(ex);
        }
    }
}
