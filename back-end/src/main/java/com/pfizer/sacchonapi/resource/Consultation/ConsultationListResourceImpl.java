package com.pfizer.sacchonapi.resource.Consultation;

import com.pfizer.sacchonapi.exception.BadEntityException;
import com.pfizer.sacchonapi.exception.NotFoundException;
import com.pfizer.sacchonapi.model.*;
import com.pfizer.sacchonapi.repository.*;
import com.pfizer.sacchonapi.repository.util.JpaUtil;
import com.pfizer.sacchonapi.representation.ConsultationRepresentation;
import com.pfizer.sacchonapi.resource.util.ResourceValidator;
import com.pfizer.sacchonapi.security.ResourceUtils;
import com.pfizer.sacchonapi.security.Shield;
import org.restlet.engine.Engine;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;


import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsultationListResourceImpl extends ServerResource implements ConsultationListResource {

    public static final Logger LOGGER = Engine.getLogger(ConsultationResource.class);

    private ConsultationRepository consultationRepository;
    private PatientRepository patientRepository;
    private ApplicationUserRepository applicationUserRepository;
    private MediDataRepository mediDataRepository;
    private String startDate;
    private String endDate;
    private long p_id;
    private long did;

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
            mediDataRepository = new MediDataRepository(em);

            try {
                startDate = getAttribute("fromdate");
                endDate = getAttribute("todate");

            } catch (Exception e) {
                startDate = null;
                endDate = null;
                System.out.println("One of the dates was null");
                LOGGER.info(e.getMessage());
            }

            p_id = Long.parseLong(getAttribute("pid"));
        } catch (Exception e) {
            p_id = -1;
            LOGGER.info(e.getMessage());
        }

        try{

            did = Long.parseLong(getAttribute("did"));
        }catch (Exception e)
        {
            did = -1;
            LOGGER.info(e.getMessage());
        }
        LOGGER.info("Initialising consultation list resource ends");
    }

    public List<ConsultationRepresentation> getConsultations() throws NotFoundException {

        LOGGER.finer("Select all consultations.");

        ResourceUtils.checkRoles(this, Shield.patient, Shield.doctor, Shield.chiefDoctor);

        List<Consultation> consultations;

        try {

            List<ConsultationRepresentation> result = new ArrayList<>();
            Optional<ApplicationUser> user = applicationUserRepository.getCurrent();

            if (p_id != -1) {

                Doctor doctorOut;
                if (user.isPresent()) {
                    doctorOut = user.get().getDoctor();
                } else {
                    LOGGER.config("This doctor cannot be found in the database.");
                    throw new NotFoundException("No doctor with this name.");
                }

                long d_id = doctorOut.getId();
                consultations = consultationRepository.findPatientCons(p_id,d_id);
            } else {

                if (did != -1) {
                  consultations = consultationRepository.findMonitoredConsultations(did, startDate, endDate);

                } else {
                    Patient patientOut;
                    if (user.isPresent()) {
                        patientOut = user.get().getPatient();
                    } else {
                        LOGGER.config("This patient cannot be found in the database.");
                        throw new NotFoundException("No patient with this name.");
                    }

                    long id = patientOut.getId();
                    consultations = consultationRepository.findPatientCons(id);
                }
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
            Date date = new Date();
            consultation.setConsultationDate(date);
            Optional<Consultation> consultationOptOut = consultationRepository.save(consultation);
            Consultation consultationOut;

            if (consultationOptOut.isPresent()) {
                consultationOut = consultationOptOut.get();

                Patient patientOut;
                Optional<Patient> patientOptional = patientRepository.findById(p_id);

                if (patientOptional.isPresent()) {
                    patientOut = patientOptional.get();
                    consultation.setPatient(patientOut);
                    patientOut.setHasConsultation(true);
                    patientOut.setConsultationPending(false);
                    patientRepository.save(patientOut);

                    List<MediData> mediData = mediDataRepository.findMediDataWithNoConsultation(patientOut.getId());
                    for (MediData m : mediData ){
                        m.setConsultation(consultation);
                    }
                }

                Optional<ApplicationUser> user = applicationUserRepository.getCurrent();

                Doctor doctorOut;
                if (user.isPresent()) {
                    doctorOut = user.get().getDoctor();
                } else {
                    LOGGER.config("This doctor cannon be found in the database.");
                    throw new NotFoundException("No doctor with this name");
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
