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
import org.restlet.engine.Engine;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsultationResourceImpl extends ServerResource implements ConsultationResource {
    public static final Logger LOGGER = Engine.getLogger(ConsultationResourceImpl.class);

    private long id;
    private ConsultationRepository consultationRepository;

    @Override
    protected void doInit() {
        LOGGER.info("Initialising consultation resource starts");
        try {
            consultationRepository =
                    new ConsultationRepository(JpaUtil.getEntityManager());
            id = Long.parseLong(getAttribute("id"));

        } catch (Exception e) {
            id = -1;
        }

        LOGGER.info("Initialising consultation resource ends");
    }

    @Override
    public ConsultationRepresentation getConsultation()
            throws NotFoundException {
        LOGGER.info("Retrieve a consultation");

        // Check authorization
        ResourceUtils.checkRole(this, Shield.ROLE_PATIENT);


        // Initialize the persistence layer.
        ConsultationRepository consultationRepository = new ConsultationRepository(JpaUtil.getEntityManager());
        Consultation consultation;
        try {


            Optional<Consultation> oconsultation = consultationRepository.findById(id);


            setExisting(oconsultation.isPresent());
            if (!isExisting()) {
                LOGGER.config("consultation id does not exist:" + id);
                throw new NotFoundException("No consultation with  : " + id);
            } else {
                consultation = oconsultation.get();
                LOGGER.finer("User allowed to retrieve a product.");
                //System.out.println(  userId);
                ConsultationRepresentation result =
                        new ConsultationRepresentation(consultation);


                LOGGER.finer("Consultation successfully retrieved");

                return result;

            }


        } catch (Exception ex) {
            throw new ResourceException(ex);
        }

    }

    @Override
    public ConsultationRepresentation store(ConsultationRepresentation consultationReprIn) throws NotFoundException, BadEntityException {
        LOGGER.finer("Update a consultation.");

        ResourceUtils.checkRole(this, Shield.ROLE_PATIENT);
        LOGGER.finer("User allowed to update a consultation.");

        // Check given entity
        ResourceValidator.notNull(consultationReprIn);
        ResourceValidator.validate(consultationReprIn);
        LOGGER.finer("Company checked");

        try {

            // Convert CompanyRepresentation to Company
            Consultation consultationIn = consultationReprIn.createConsulation();
            consultationIn.setId(id);

            Optional<Consultation> consultationOut;

            Optional<Consultation> oConsultation = consultationRepository.findById(id);

            setExisting(oConsultation.isPresent());

            // If product exists, we update it.
            if (isExisting()) {
                LOGGER.finer("Update product.");

                // Update product in DB and retrieve the new one.
                consultationOut = consultationRepository.update(consultationIn);


                // Check if retrieved product is not null : if it is null it
                // means that the id is wrong.
                if (!consultationOut.isPresent()) {
                    LOGGER.finer("Consultation does not exist.");
                    throw new NotFoundException(
                            "Consultation with the following id does not exist: "
                                    + id);
                }
            } else {
                LOGGER.finer("Resource does not exist.");
                throw new NotFoundException(
                        "Company with the following id does not exist: " + id);
            }

            LOGGER.finer("Company successfully updated.");
            return new ConsultationRepresentation(consultationOut.get());

        } catch (Exception ex) {

            throw new ResourceException(ex);
        }
    }
}
