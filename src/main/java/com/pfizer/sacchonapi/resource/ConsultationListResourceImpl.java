package com.pfizer.sacchonapi.resource;

import com.pfizer.sacchonapi.exception.BadEntityException;
import com.pfizer.sacchonapi.exception.NotFoundException;
import com.pfizer.sacchonapi.model.Consultation;
import com.pfizer.sacchonapi.repository.ConsultationRepository;
import com.pfizer.sacchonapi.repository.util.JpaUtil;
import com.pfizer.sacchonapi.representation.ConsultationRepresentation;
import com.pfizer.sacchonapi.resource.util.ResourceValidator;
import com.pfizer.sacchonapi.security.ResourceUtils;
import com.pfizer.sacchonapi.security.Shield;
import org.restlet.data.Status;
import org.restlet.engine.Engine;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsultationListResourceImpl extends ServerResource implements ConsultationListResource {

    public static final Logger LOGGER = Engine.getLogger(ConsultationResource.class);

    private ConsultationRepository consultationRepository;

    private String startDate;
    private String endDate;

    @Override
    protected void doInit() {
        LOGGER.info("Initialising consultation resource starts");
        try {
            consultationRepository = new ConsultationRepository(JpaUtil.getEntityManager());
        } catch (Exception e) {
            throw new ResourceException(e);
        }
        LOGGER.info("Initialising consultation resource ends");
    }

    public List<ConsultationRepresentation> getConsultations() throws NotFoundException {

        LOGGER.finer("Select all consultations.");

        ResourceUtils.checkRoles(this, Shield.ROLE_PATIENT, Shield.ROLE_DOCTOR,Shield.ROLE_CHIEF_DOCTOR);

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
    public ConsultationRepresentation add (ConsultationRepresentation consultationIn) throws BadEntityException {
        LOGGER.finer("Add a new consultation.");

        ResourceUtils.checkRole(this, Shield.ROLE_PATIENT);
        LOGGER.finer("User allowed to add a consultation.");

        ResourceValidator.notNull(consultationIn);
        ResourceValidator.validate(consultationIn);
        LOGGER.finer("Consultation checked");

        try {
            Consultation consultation = consultationIn.createConsulation();

            Optional<Consultation> consultationOptOut = consultationRepository.save(consultation);

            Consultation consultationOut;
            if (consultationOptOut.isPresent())
                consultationOut = consultationOptOut.get();
            else
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
