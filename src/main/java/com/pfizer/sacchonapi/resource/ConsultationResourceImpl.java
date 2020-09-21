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

import javax.persistence.EntityManager;
import java.util.Optional;
import java.util.logging.Logger;

public class ConsultationResourceImpl extends ServerResource implements ConsultationResource {
    public static final Logger LOGGER = Engine.getLogger(ConsultationResourceImpl.class);
    private long cid;
    private ConsultationRepository consultationRepository;
    private EntityManager em;

    @Override
    protected void doRelease()
    {
        em.close();
    }

    @Override
    protected void doInit() {
        LOGGER.info("Initialising consultation resource starts");
        try {
            em = JpaUtil.getEntityManager();
            consultationRepository =
                    new ConsultationRepository(em);
            cid = Long.parseLong(getAttribute("cid"));

        } catch (Exception e) {
            cid = -1;
        }

        LOGGER.info("Initialising consultation resource ends");
    }

    @Override
    public ConsultationRepresentation getConsultation() throws NotFoundException {
        LOGGER.info("Retrieve a consultation");

        ResourceUtils.checkRole(this, Shield.patient);

        ConsultationRepository consultationRepository = new ConsultationRepository(JpaUtil.getEntityManager());
        Consultation consultation;
        try {

            Optional<Consultation> oconsultation = consultationRepository.findById(cid);

            setExisting(oconsultation.isPresent());
            if (!isExisting()) {
                LOGGER.config("consultation id does not exist:" + cid);
                throw new NotFoundException("No consultation with  : " + cid);
            } else {
                consultation = oconsultation.get();
                LOGGER.finer("User allowed to retrieve a product.");

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
        ResourceUtils.checkRole(this, Shield.patient);
        LOGGER.finer("User allowed to update a consultation.");

        ResourceValidator.notNull(consultationReprIn);
        ResourceValidator.validate(consultationReprIn);
        LOGGER.finer("Company checked");
        try {
            Consultation consultationIn = consultationReprIn.createConsulation();
            consultationIn.setId(cid);
            Optional<Consultation> consultationOut;
            Optional<Consultation> oConsultation = consultationRepository.findById(cid);
            setExisting(oConsultation.isPresent());
            if (isExisting()) {
                LOGGER.finer("Update consultation.");
                consultationOut = consultationRepository.update(consultationIn);
                if (!consultationOut.isPresent()) {
                    LOGGER.finer("Consultation does not exist.");
                    throw new NotFoundException("Consultation with the following id does not exist: " + cid);
                }
            } else {
                LOGGER.finer("Resource does not exist.");
                throw new NotFoundException("Company with the following id does not exist: " + cid);
            }
            LOGGER.finer("Company successfully updated.");
            return new ConsultationRepresentation(consultationOut.get());
        } catch (Exception ex) {
            throw new ResourceException(ex);
        }
    }
}
