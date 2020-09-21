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
    private String patient_id;

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
            patient_id = getAttribute("p_id");
        } catch (Exception e) {
            throw new ResourceException(e);
        }
        LOGGER.info("Initialising consultation resource ends");
    }

    public List<ConsultationRepresentation> getConsultations() throws NotFoundException {

        LOGGER.finer("Select all consultations.");

        ResourceUtils.checkRoles(this, Shield.patient, Shield.doctor, Shield.chiefDoctor);

        try {
            List<Consultation> consultations = consultationRepository.findAll();
            List<ConsultationRepresentation> result = new ArrayList<>();

            consultations.forEach(consultation ->
                    result.add(new ConsultationRepresentation(consultation)));

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

        Long p_id = 0l;

                p_id = Long.valueOf(patient_id).longValue();


            Consultation consultation = consultationIn.createConsulation();

            Optional<Consultation> consultationOptOut = consultationRepository.save(consultation);
            Consultation consultationOut;

            if (consultationOptOut.isPresent()) {
                consultationOut = consultationOptOut.get();

                Patient patientOut;
                Optional<Patient> patientOptional = patientRepository.findById(p_id);


                    patientOut = patientOptional.get();
                    consultation.setPatient(patientOut);


                Request request = Request.getCurrent();
                String username = request.getClientInfo().getUser().getName();
                Optional<ApplicationUser> user = applicationUserRepository.findByUsername(username);

                Doctor doctorOut;

                    doctorOut = user.get().getDoctor();


                consultation.setDoctor(doctorOut);

                consultationRepository.save(consultation);
            } else
                throw new BadEntityException(" Consultation has not been created");

            ConsultationRepresentation result = new ConsultationRepresentation(consultationOut);

            LOGGER.finer("Consultation successfully added.");

            return result;


    }
}